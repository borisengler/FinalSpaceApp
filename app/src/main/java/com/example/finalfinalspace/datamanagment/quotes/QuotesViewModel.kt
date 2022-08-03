package com.example.finalfinalspace.datamanagment.quotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalfinalspace.di.qualifiers.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuotesViewModel @Inject constructor(
    quotesManager: QuotesManager,
    @IoDispatcher var dispatcher: CoroutineDispatcher
) : ViewModel() {

    init {
        viewModelScope.launch(dispatcher) {
            quotesManager.downloadQuotes()
        }
    }

    val quotes = quotesManager.quotes
}
