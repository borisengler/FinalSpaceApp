package com.example.finalfinalspace.datamanagment.quotes

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QuotesViewModel @Inject constructor(
    quotesManager: QuotesManager
) : ViewModel() {

    val quotes = quotesManager.quotes
}
