package com.example.finalfinalspace.domain

import com.example.finalfinalspace.data.api.FinalSpaceAPI
import com.example.finalfinalspace.data.api.models.EpisodesWithCharsInfo
import com.example.finalfinalspace.data.db.CharInEpiDAO
import com.example.finalfinalspace.data.db.EpisodesDAO
import com.example.finalfinalspace.data.db.models.CharInEpiInfo
import com.example.finalfinalspace.data.db.models.CharactersInfo
import com.example.finalfinalspace.data.db.models.EpisodeWithCharactersInfo
import com.example.finalfinalspace.data.db.models.EpisodesInfo
import com.example.finalfinalspace.extensions.toDatabase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class EpisodesManagerTest {

    @MockK private lateinit var episodesDAO: EpisodesDAO
    @MockK private lateinit var charInEpiDAO: CharInEpiDAO
    @MockK private lateinit var finalSpaceAPI: FinalSpaceAPI

    private lateinit var episodesManager: EpisodesManager

    companion object {
        private val DOWNLOAD_EXCEPTION = Throwable("Unable to download data")
        private val EPISODE_WITH_CHARACTERS_INFO =
            EpisodeWithCharactersInfo(
                EpisodesInfo(1, "ep1", "1.1.2020", "direc", "writer", "url"),
                listOf(
                    CharactersInfo(1, "char1", "url1"),
                    CharactersInfo(2, "char2", "url2")
                )
            )
        private val ALL_EPISODES = flowOf(
            listOf(
                EpisodesInfo(1, "ep1", "1.1.2020", "direc", "writer", "url"),
                EpisodesInfo(2, "ep2", "2.2.2020", "direc", "writer", "url2"),
            )
        )
        private val EPISODES_FROM_API = listOf(
            EpisodesWithCharsInfo(
                1, "ep1", "1.1.2020", "direc", "writer", "url",
                listOf("apiCharUrl/1", "apiCharUrl/2")
            )
        )
        private val INSERTING_EPISODES = EPISODES_FROM_API.map { it.toDatabase() }
        private val INSERTING_EPISODES_RESULT = listOf<Long>(1)
        private val CHAR_IN_EPI_INSERT = EPISODES_FROM_API.map {
            it.characters.map { character -> CharInEpiInfo(it.id, character.split("/").last().toInt()) }
        }.flatten()
        private val CHAR_IN_EPI_INSERT_RESULT = listOf<Long>(1, 2)
    }

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        every { episodesDAO.getAllEpisodes() } returns ALL_EPISODES

        episodesManager = EpisodesManager(episodesDAO, charInEpiDAO, finalSpaceAPI)
    }

    @Test
    fun testFetchEpisodeWithCharactersWhenFound() = runTest {
        coEvery { episodesDAO.fetchEpisodeWithCharacters(1) } returns EPISODE_WITH_CHARACTERS_INFO andThen null
        val fetchedEpisode = episodesManager.fetchEpisodeWithCharacters(1)

        coVerify(exactly = 1) { episodesDAO.fetchEpisodeWithCharacters(1) }
        Assert.assertEquals(EPISODE_WITH_CHARACTERS_INFO, fetchedEpisode)
    }

    @Test
    fun testFetchEpisodeWithCharactersWhenNotFound() = runTest {
        coEvery { episodesDAO.fetchEpisodeWithCharacters(1) } returns null
        val fetchedEpisode = episodesManager.fetchEpisodeWithCharacters(1)

        coVerify(exactly = 1) { episodesDAO.fetchEpisodeWithCharacters(1) }
        Assert.assertEquals(null, fetchedEpisode)
    }

    @Test
    fun testDownloadEpisodesWhenSuccess() = runTest {
        coEvery { finalSpaceAPI.getEpisodes() } returns EPISODES_FROM_API
        coEvery { episodesDAO.insertAll(INSERTING_EPISODES) } returns INSERTING_EPISODES_RESULT
        coEvery { charInEpiDAO.insertAll(CHAR_IN_EPI_INSERT) } returns CHAR_IN_EPI_INSERT_RESULT

        episodesManager.downloadEpisodes()

        coVerify(exactly = 1) { finalSpaceAPI.getEpisodes() }
        coVerify(exactly = 1) { episodesDAO.insertAll(INSERTING_EPISODES) }
        coVerify(exactly = 1) { charInEpiDAO.insertAll(CHAR_IN_EPI_INSERT) }
    }

    @Test()
    fun testDownloadEpisodesDownloadFails() = runTest {
        coEvery { finalSpaceAPI.getEpisodes() } throws DOWNLOAD_EXCEPTION

        runCatching {
            episodesManager.downloadEpisodes()
        }.onFailure {
            Assert.assertEquals(DOWNLOAD_EXCEPTION, it)
        }

        coVerify(exactly = 1) { finalSpaceAPI.getEpisodes() }
        coVerify(exactly = 0) { episodesDAO.insertAll(any()) }
        coVerify(exactly = 0) { charInEpiDAO.insertAll(any()) }
    }
}
