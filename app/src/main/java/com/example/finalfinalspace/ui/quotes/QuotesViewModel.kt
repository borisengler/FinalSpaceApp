package com.example.finalfinalspace.ui.quotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalfinalspace.data.db.models.CharactersInfo
import com.example.finalfinalspace.data.db.models.QuoteOrCharacter
import com.example.finalfinalspace.data.db.models.QuotesInfo
import com.example.finalfinalspace.di.qualifiers.IoDispatcher
import com.example.finalfinalspace.domain.QuotesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
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

    val quotesByCharacters : Flow<List<QuoteOrCharacter>> =
        quotesManager.quotesByCharacters.map { listOfCharactersWithQuotes ->
        val items = mutableListOf<QuoteOrCharacter>()
        listOfCharactersWithQuotes.forEach {
            items += it.character
            items += it.quotes
        }
        return@map items
    }.flowOn(ioDispatcher)

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
