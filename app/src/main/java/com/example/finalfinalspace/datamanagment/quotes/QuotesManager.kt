package com.example.finalfinalspace.datamanagment.quotes

import com.example.finalfinalspace.datamanagment.FinalSpaceAPI
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class QuotesManager @Inject constructor(
    private val quotesDAO: QuotesDAO,
    private val finalSpaceAPI: FinalSpaceAPI
) {

    val quotes = quotesDAO.getAllQuotes().distinctUntilChanged()

    suspend fun downloadQuotes() {
        quotesDAO.insertAll(finalSpaceAPI.getQuotes())
    }
}