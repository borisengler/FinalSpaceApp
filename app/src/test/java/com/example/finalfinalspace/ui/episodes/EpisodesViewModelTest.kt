package com.example.finalfinalspace.ui.episodes

import com.example.finalfinalspace.data.db.models.CharactersInfo
import com.example.finalfinalspace.data.db.models.EpisodeWithCharactersInfo
import com.example.finalfinalspace.data.db.models.EpisodesInfo
import com.example.finalfinalspace.domain.CharactersManager
import com.example.finalfinalspace.domain.EpisodesManager
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
class EpisodesViewModelTest {

    companion object {
        private val EXCEPTION = Throwable("Failed to download data")
        private val LIST_OF_EPISODES: List<EpisodesInfo> = listOf(
            EpisodesInfo(1, "episode 1", "01.01.2020", "Test director 1", "Test writer 1 ", "imageUrl1"),
            EpisodesInfo(2, "episode 2", "02.02.2020", "Test director 2", "Test writer 2", "imageUrl2"),
            EpisodesInfo(3, "episode 3", "03.03.2020", "Test director 3", "Test writer 3", "imageUrl3")
        )
        private val EPISODES_FLOW = flowOf(LIST_OF_EPISODES)
        private val EPISODE_WITH_CHARACTERS = EpisodeWithCharactersInfo(
            EpisodesInfo(1, "episode 1", "01.01.2020", "Test director 1", "Test writer 1 ", "imageUrl1"),
            listOf(
                CharactersInfo(1, "Character 1", "charImageUrl1"),
                CharactersInfo(2, "Character 2", "charImageUrl2")
            )
        )
    }

    @MockK private lateinit var episodesManager: EpisodesManager
    @MockK private lateinit var charactersManager: CharactersManager

    private lateinit var episodesViewModel: EpisodesViewModel

    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        coEvery { episodesManager.episodes } returns EPISODES_FLOW
        episodesViewModel = EpisodesViewModel(
            episodesManager,
            charactersManager,
            Dispatchers.Unconfined,
            testDispatcher
        )
    }

    @Test
    fun testLoadEpisodeWithCharactersWhenValid() = runTest(testDispatcher) {
        coEvery { episodesManager.fetchEpisodeWithCharacters(1) } returns EPISODE_WITH_CHARACTERS
        val episode = mutableListOf<EpisodeWithCharactersInfo?>()
        val job = launch {
            episodesViewModel.episodeWithCharacters.toList(episode)
        }


        episodesViewModel.loadEpisodeWithCharacters(1)

        assert(episode.last() != null)
        coVerify(exactly = 1) { episodesManager.fetchEpisodeWithCharacters(1) }

        job.cancel()
    }

    @Test
    fun testLoadEpisodeWithCharactersWhenInvalid() = runTest(testDispatcher) {
        coEvery { episodesManager.fetchEpisodeWithCharacters(1) } returns null
        val episode = mutableListOf<EpisodeWithCharactersInfo?>()
        val job = launch {
            episodesViewModel.episodeWithCharacters.toList(episode)
        }

        episodesViewModel.loadEpisodeWithCharacters(1)

        assert(episode.last() == null)
        coVerify(exactly = 1) { episodesManager.fetchEpisodeWithCharacters(1) }

        job.cancel()
    }

    @Test
    fun testDownloadDataWhenSuccess() = runTest {
        mockkObject(Timber)
        val results = mutableListOf<Boolean>()
        val errorMessage = mutableListOf<Unit>()
        episodesViewModel.downloading.onEach {
            results.add(it)
        }.launchIn(CoroutineScope(UnconfinedTestDispatcher(testScheduler)))
        episodesViewModel.errorMessage.onEach { errorMessage.add(it) }.launchIn(
            CoroutineScope(
                UnconfinedTestDispatcher(
                    testScheduler
                )
            )
        )

        episodesViewModel.downloadData()

        Assert.assertEquals(listOf(false, true, false), results)
        assert(errorMessage.isEmpty())

        coVerify(exactly = 1) { charactersManager.downloadCharacters() }
        coVerify(exactly = 1) { episodesManager.downloadEpisodes() }
        coVerify(exactly = 0) { Timber.e(EXCEPTION) }
        unmockkAll()
    }

    @Test
    fun testDownloadDataWhenFailure() = runTest {
        mockkObject(Timber)
        coEvery { charactersManager.downloadCharacters() } throws EXCEPTION
        val results = mutableListOf<Boolean>()
        val errorMessage = mutableListOf<Unit>()
        episodesViewModel.downloading.onEach {
            results.add(it)
        }.launchIn(CoroutineScope(UnconfinedTestDispatcher(testScheduler)))
        episodesViewModel.errorMessage.onEach { errorMessage.add(it) }.launchIn(
            CoroutineScope(
                UnconfinedTestDispatcher(
                    testScheduler
                )
            )
        )

        episodesViewModel.downloadData()

        Assert.assertEquals(listOf(false, true, false), results)
        assert(errorMessage.isNotEmpty())

        coVerify(exactly = 1) { charactersManager.downloadCharacters() }
        coVerify(exactly = 0) { episodesManager.downloadEpisodes() }
        coVerify(exactly = 1) { Timber.e(EXCEPTION) }
        unmockkAll()
    }
}
