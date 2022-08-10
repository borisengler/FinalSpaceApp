package com.example.finalfinalspace.domain

import com.example.finalfinalspace.data.api.FinalSpaceAPI
import com.example.finalfinalspace.data.db.CharactersDAO
import com.example.finalfinalspace.data.db.models.CharactersInfo
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CharactersManagerTest {

    @MockK private lateinit var charactersDAO: CharactersDAO
    @MockK private lateinit var finalSpaceAPI: FinalSpaceAPI

    private lateinit var charactersManager: CharactersManager

    companion object {
        private val DOWNLOAD_EXCEPTION = Throwable("Unable to download data")
        private val CHARACTERS = listOf(
            CharactersInfo(1, "char1", "url1"),
            CharactersInfo(2, "char2", "url2"),
            CharactersInfo(3, "char3", "url3"),
            CharactersInfo(4, "char4", "url4")
        )
        private val CHARACTERS_INSERT_RESULT = listOf<Long>(1, 2, 3, 4)
    }

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        charactersManager = CharactersManager(charactersDAO, finalSpaceAPI)
    }

    @Test
    fun testDownloadCharactersWhenSuccess() = runTest {
        coEvery { finalSpaceAPI.getCharacters() } returns CHARACTERS
        coEvery { charactersDAO.insertAll(CHARACTERS) } returns CHARACTERS_INSERT_RESULT
        charactersManager.downloadCharacters()
        coVerify(exactly = 1) { finalSpaceAPI.getCharacters() }
        coVerify(exactly = 1) { charactersDAO.insertAll(CHARACTERS) }
    }

    @Test
    fun testDownloadCharactersWhenDownloadFail() = runTest {
        coEvery { finalSpaceAPI.getCharacters() } throws DOWNLOAD_EXCEPTION
        runCatching {
            charactersManager.downloadCharacters()
        }.onFailure {
            Assert.assertEquals(DOWNLOAD_EXCEPTION, it)
        }

        coVerify(exactly = 1) { finalSpaceAPI.getCharacters() }
        coVerify(exactly = 0) { charactersDAO.insertAll(CHARACTERS) }
    }
}
