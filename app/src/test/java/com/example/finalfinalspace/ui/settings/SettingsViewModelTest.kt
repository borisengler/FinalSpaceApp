package com.example.finalfinalspace.ui.settings

import com.example.finalfinalspace.BuildConfig
import com.example.finalfinalspace.data.prefs.SettingsStorage
import com.example.finalfinalspace.domain.CharactersManager
import com.example.finalfinalspace.domain.EpisodesManager
import com.example.finalfinalspace.domain.QuotesManager
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockkObject
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import timber.log.Timber

@ExperimentalCoroutinesApi
class SettingsViewModelTest {

    companion object {
        private val EXCEPTION = Throwable("Failed to download data")
    }

    @MockK private lateinit var episodesManager: EpisodesManager
    @MockK private lateinit var charactersManager: CharactersManager
    @MockK private lateinit var quotesManager: QuotesManager
    @MockK private lateinit var settingsStorage: SettingsStorage

    lateinit var settingsViewModel: SettingsViewModel
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        settingsViewModel = SettingsViewModel(
            episodesManager,
            charactersManager,
            quotesManager,
            settingsStorage,
            testDispatcher
        )
    }

    @Test
    fun testGetVersion() {
        val version = settingsViewModel.getVersion()
        assertEquals(BuildConfig.VERSION_NAME, version)
    }

    @Test
    fun testSetAutoSyncWhenTrue() {
        settingsViewModel.setAutoSync(true)
        verify(exactly = 1) { settingsStorage.setAutoSync(true) }
    }

    @Test
    fun testSetAutoSyncWhenFalse() {
        settingsViewModel.setAutoSync(false)
        verify(exactly = 1) { settingsStorage.setAutoSync(false) }
    }

    @Test
    fun testDownloadDataWhenSuccess() = runTest {
        mockkObject(Timber)
        val results = mutableListOf<Boolean>()
        settingsViewModel.downloadStatus.onEach {
            results.add(it)
        }.launchIn(CoroutineScope(UnconfinedTestDispatcher(testScheduler)))

        settingsViewModel.downloadData()

        assertEquals(true, results.last())
        coVerify(exactly = 1) { charactersManager.downloadCharacters() }
        coVerify(exactly = 1) { episodesManager.downloadEpisodes() }
        coVerify(exactly = 1) { quotesManager.downloadQuotes() }
        coVerify(exactly = 0) { Timber.e(EXCEPTION) }
        unmockkAll()
    }

    @Test
    fun testDownloadDataWhenFailure() = runTest {
        mockkObject(Timber)
        coEvery { charactersManager.downloadCharacters() } throws EXCEPTION
        val results = mutableListOf<Boolean>()
        settingsViewModel.downloadStatus.onEach {
            results.add(it)
        }.launchIn(CoroutineScope(UnconfinedTestDispatcher(testScheduler)))

        settingsViewModel.downloadData()

        assertEquals(false, results.last())
        coVerify(exactly = 1) { charactersManager.downloadCharacters() }
        coVerify(exactly = 0) { episodesManager.downloadEpisodes() }
        coVerify(exactly = 0) { quotesManager.downloadQuotes() }
        coVerify(exactly = 1) { Timber.e(EXCEPTION) }
        unmockkAll()
    }
}
