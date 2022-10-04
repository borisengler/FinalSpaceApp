package com.example.finalfinalspace.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.insertSeparators
import com.example.finalfinalspace.data.api.FinalSpaceAPI
import com.example.finalfinalspace.data.db.CharactersDAO
import com.example.finalfinalspace.data.db.QuotesDAO
import com.example.finalfinalspace.data.db.models.QuoteOrCharacter
import com.example.finalfinalspace.data.db.models.QuotesInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class QuotesManager @Inject constructor(
    private val quotesDAO: QuotesDAO,
    private val charactersDAO: CharactersDAO,
    private val finalSpaceAPI: FinalSpaceAPI
) {

    companion object {
        const val PAGE_SIZE = 30
    }

    val quotes = quotesDAO.getAllQuotes().distinctUntilChanged()

    suspend fun downloadQuotes() {
        val quotes = finalSpaceAPI.getQuotes()
        quotesDAO.insertAll(quotes)
    }

    suspend fun addQuote(quotesInfo: QuotesInfo) {
        quotesDAO.insertQuote(quotesInfo)
    }

    fun getFilteredQuotes(filter: String): Flow<PagingData<QuoteOrCharacter>> {
        val sqlFilter = if (filter.isNotEmpty()) {
            "%$filter%"
        } else {
            "%"
        }
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            pagingSourceFactory = { quotesDAO.getFilteredQuotes(sqlFilter) }
        ).flow.map {
            it.insertSeparators { quotesInfo1: QuotesInfo?, quotesInfo2: QuotesInfo? ->
                return@insertSeparators if (quotesInfo1 == null) {
                    if (quotesInfo2 == null) {
                        return@insertSeparators null
                    }
                    charactersDAO.fetchCharacterByName(quotesInfo2.by)
                } else if (quotesInfo2 == null) {
                    null
                } else if (quotesInfo1.by != quotesInfo2.by) {
                    charactersDAO.fetchCharacterByName(quotesInfo2.by)
                } else {
                    null
                }
            }
        }
    }
}
