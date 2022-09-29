package com.example.finalfinalspace.domain

import com.example.finalfinalspace.data.api.FinalSpaceAPI
import com.example.finalfinalspace.data.db.QuotesDAO
import com.example.finalfinalspace.data.db.models.CharactersInfo
import com.example.finalfinalspace.data.db.models.QuotesInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class QuotesManager @Inject constructor(
    private val quotesDAO: QuotesDAO,
    private val finalSpaceAPI: FinalSpaceAPI
) {

    val quotes = quotesDAO.getAllQuotes().distinctUntilChanged()

    suspend fun downloadQuotes() {
        val quotes = finalSpaceAPI.getQuotes()
        quotesDAO.insertAll(quotes)
    }

    fun getFilteredQuotes(filter: String): Flow<Map<CharactersInfo, List<QuotesInfo>>> {
        val sqlFilter = if (filter.isNotEmpty()) {
            "%$filter%"
        } else {
            "%"
        }
        return quotesDAO.getFilteredQuotes(sqlFilter).distinctUntilChanged()
    }
}
