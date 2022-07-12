package com.example.finalfinalspace.datamanagment.quotes

import androidx.lifecycle.ViewModel
import com.example.finalfinalspace.datamanagment.network.QuotesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QuotesViewModel @Inject constructor(
    private val quotesManager: QuotesManager
) : ViewModel() {

    val quotes = quotesManager.quotes

}
