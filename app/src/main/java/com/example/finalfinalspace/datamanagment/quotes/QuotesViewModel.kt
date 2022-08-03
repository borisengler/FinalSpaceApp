package com.example.finalfinalspace.datamanagment.quotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalfinalspace.di.qualifiers.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuotesViewModel @Inject constructor(
    private val quotesManager: QuotesManager,
    @IoDispatcher val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _errorMessage = MutableSharedFlow<Unit>()
    val errorMessage = _errorMessage.asSharedFlow()

    init {
        downloadData()
    }

    val quotes = quotesManager.quotes

    fun downloadData() {
        viewModelScope.launch(ioDispatcher) {
            runCatching {
                quotesManager.downloadQuotes()
            }.onFailure {
                _errorMessage.emit(Unit)
            }
        }
    }
}
