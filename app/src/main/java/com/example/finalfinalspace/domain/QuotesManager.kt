package com.example.finalfinalspace.domain

import com.example.finalfinalspace.data.api.FinalSpaceAPI
import com.example.finalfinalspace.data.db.QuotesDAO
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class QuotesManager @Inject constructor(
    private val quotesDAO: QuotesDAO,
    private val finalSpaceAPI: FinalSpaceAPI
) {

    val quotes = quotesDAO.getAllQuotes().distinctUntilChanged()
    val quotesByCharacters = quotesDAO.getCharactersWithQuotes().distinctUntilChanged()

    suspend fun downloadQuotes() {
        val quotes = finalSpaceAPI.getQuotes()
        quotesDAO.insertAll(quotes)
    }
}
