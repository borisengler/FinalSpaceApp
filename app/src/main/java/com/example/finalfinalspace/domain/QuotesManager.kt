package com.example.finalfinalspace.domain

import com.example.finalfinalspace.data.api.FinalSpaceAPI
import com.example.finalfinalspace.data.db.QuotesDAO
import com.example.finalfinalspace.data.db.models.CharacterWithQuotesInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class QuotesManager @Inject constructor(
    private val quotesDAO: QuotesDAO,
    private val finalSpaceAPI: FinalSpaceAPI
) {

    val quotes = quotesDAO.getAllQuotes().distinctUntilChanged()

    fun getQuotesByCharacters(filter: String): Flow<List<CharacterWithQuotesInfo>> {
        val sqlFilter = if (filter.isNotEmpty()) {
            "%$filter%"
        } else {
            "%"
        }
        println("fetching filtered quotes, filter: '$sqlFilter'")
        if (filter.isEmpty()) {
            return quotesDAO.getCharactersWithQuotes().distinctUntilChanged()
        }
        return quotesDAO.getCharactersWithQuotes(sqlFilter).distinctUntilChanged()
    }

    suspend fun downloadQuotes() {
        val quotes = finalSpaceAPI.getQuotes()
        quotesDAO.insertAll(quotes)
    }
}
