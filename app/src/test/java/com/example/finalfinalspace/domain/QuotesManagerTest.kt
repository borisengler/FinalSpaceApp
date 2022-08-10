package com.example.finalfinalspace.domain

import com.example.finalfinalspace.data.api.FinalSpaceAPI
import com.example.finalfinalspace.data.db.QuotesDAO
import com.example.finalfinalspace.data.db.models.CharacterWithQuotesInfo
import com.example.finalfinalspace.data.db.models.CharactersInfo
import com.example.finalfinalspace.data.db.models.QuotesInfo
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class QuotesManagerTest {

    @MockK private lateinit var quotesDAO: QuotesDAO
    @MockK private lateinit var finalSpaceAPI: FinalSpaceAPI

    private lateinit var quotesManager: QuotesManager

    companion object {
        private val DOWNLOAD_EXCEPTION = Throwable("Unable to download data")
        private val QUOTES = listOf(
            QuotesInfo(1, "q1", "a1"),
            QuotesInfo(2, "q2", "a1"),
            QuotesInfo(3, "q3", "a1"),
            QuotesInfo(4, "q4", "a2")
        )
        private val QUOTES_FLOW = flowOf(QUOTES)
        private val charactersWithQuotes: Flow<List<CharacterWithQuotesInfo>> = flowOf(
            listOf(
                CharacterWithQuotesInfo(
                    CharactersInfo(1, "a1", "url1"),
                    listOf(
                        QuotesInfo(1, "q1", "a1"),
                        QuotesInfo(2, "q2", "a1"),
                        QuotesInfo(3, "q3", "a1")
                    )
                ),
                CharacterWithQuotesInfo(
                    CharactersInfo(2, "a2", "url2"),
                    listOf(QuotesInfo(4, "q4", "a2"))
                ),
            )
        )
        private val QUOTES_INSERT_RESULT = listOf<Long>(1, 2, 3, 4)
    }

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        every { quotesDAO.getAllQuotes() } returns QUOTES_FLOW
        every { quotesDAO.getCharactersWithQuotes() } returns charactersWithQuotes

        quotesManager = QuotesManager(quotesDAO, finalSpaceAPI)
    }

    @Test
    fun testDownloadQuotesWhenSuccess() = runTest {
        coEvery { finalSpaceAPI.getQuotes() } returns QUOTES
        coEvery { quotesDAO.insertAll(QUOTES) } returns QUOTES_INSERT_RESULT
        quotesManager.downloadQuotes()
        coVerify(exactly = 1) { finalSpaceAPI.getQuotes() }
        coVerify(exactly = 1) { quotesDAO.insertAll(QUOTES) }
    }

    @Test
    fun testDownloadCharactersWhenDownloadFails() = runTest {
        coEvery { finalSpaceAPI.getQuotes() } throws DOWNLOAD_EXCEPTION
        runCatching {
            quotesManager.downloadQuotes()
        }.onFailure {
            Assert.assertEquals(DOWNLOAD_EXCEPTION, it)
        }
        coVerify(exactly = 1) { finalSpaceAPI.getQuotes() }
        coVerify(exactly = 0) { quotesDAO.insertAll(QUOTES) }
    }
}
