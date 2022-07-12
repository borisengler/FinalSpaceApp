package com.example.finalfinalspace.datamanagment.network

import androidx.lifecycle.distinctUntilChanged
import com.example.finalfinalspace.datamanagment.FinalSpaceAPI
import com.example.finalfinalspace.datamanagment.quotes.QuotesDAO
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