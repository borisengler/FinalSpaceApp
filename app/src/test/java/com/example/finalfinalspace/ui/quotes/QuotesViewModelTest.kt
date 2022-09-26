package com.example.finalfinalspace.ui.quotes

import com.example.finalfinalspace.data.db.models.CharacterWithQuotesInfo
import com.example.finalfinalspace.data.db.models.CharactersInfo
import com.example.finalfinalspace.data.db.models.QuotesInfo
import com.example.finalfinalspace.domain.QuotesManager
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockkObject
import io.mockk.unmockkAll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import timber.log.Timber

@ExperimentalCoroutinesApi
class QuotesViewModelTest {

    @MockK private lateinit var quotesManager: QuotesManager
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()

    lateinit var quotesViewModel: QuotesViewModel

    companion object {
        private val EXCEPTION = Throwable("Failed to download data")
        private val QUOTES_BY_CHARACTER_FLOW = flowOf(
            mapOf(
                CharactersInfo(1, "char", "url") to
                listOf(
                    QuotesInfo(1, "Quote", "char"),
                    QuotesInfo(1, "Quote", "char")
                )
            )
        )
        private val QUOTES_FLOW = flowOf(
            listOf(
                QuotesInfo(1, "Q1", "A1"),
                QuotesInfo(1, "Q2", "A2")
            )
        )
        private val QUOTES_BY_CHARACTER_RESULT = flowOf(
            listOf(
                CharactersInfo(1, "char", "url"),
                QuotesInfo(1, "Quote", "char"),
                QuotesInfo(1, "Quote", "char")
            )
        )

    }

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        coEvery { quotesManager.quotes } returns QUOTES_FLOW
        coEvery { quotesManager.getFilteredQuotes("") } returns QUOTES_BY_CHARACTER_FLOW


        quotesViewModel = QuotesViewModel(quotesManager, Dispatchers.Unconfined, testDispatcher)
    }

    @Test
    fun testDownloadDataWhenSuccess() = runTest {
        mockkObject(Timber)
        val results = mutableListOf<Boolean>()
        val errorMessage = mutableListOf<Unit>()
        quotesViewModel.downloading.onEach {
            results.add(it)
        }.launchIn(CoroutineScope(UnconfinedTestDispatcher(testScheduler)))
        quotesViewModel.errorMessage.onEach { errorMessage.add(it) }.launchIn(
            CoroutineScope(
                UnconfinedTestDispatcher(
                    testScheduler
                )
            )
        )

        quotesViewModel.downloadData()

        Assert.assertEquals(listOf(false, true, false), results)
        assert(errorMessage.isEmpty())

        coVerify(exactly = 1) { quotesManager.downloadQuotes() }
        coVerify(exactly = 0) { Timber.e(EXCEPTION) }
        unmockkAll()
    }

    @Test
    fun testDownloadDataWhenFailure() = runTest {
        mockkObject(Timber)
        coEvery { quotesManager.downloadQuotes() } throws EXCEPTION
        val results = mutableListOf<Boolean>()
        val errorMessage = mutableListOf<Unit>()
        quotesViewModel.downloading.onEach {
            results.add(it)
        }.launchIn(CoroutineScope(UnconfinedTestDispatcher(testScheduler)))
        quotesViewModel.errorMessage.onEach { errorMessage.add(it) }.launchIn(
            CoroutineScope(
                UnconfinedTestDispatcher(
                    testScheduler
                )
            )
        )

        quotesViewModel.downloadData()

        Assert.assertEquals(listOf(false, true, false), results)
        assert(errorMessage.isNotEmpty())

        coVerify(exactly = 1) { quotesManager.downloadQuotes() }
        coVerify(exactly = 1) { Timber.e(EXCEPTION) }
        unmockkAll()
    }

    @Test
    fun testQuotesByCharacters() = runTest {
        launch {
            quotesViewModel.getFilteredQuotes()
            Assert.assertEquals(QUOTES_BY_CHARACTER_RESULT.toList(), quotesViewModel.quotes.toList())
        }
    }
}
