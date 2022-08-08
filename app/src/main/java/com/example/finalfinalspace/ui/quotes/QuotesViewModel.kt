package com.example.finalfinalspace.ui.quotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalfinalspace.data.db.models.QuoteOrCharacter
import com.example.finalfinalspace.di.qualifiers.IoDispatcher
import com.example.finalfinalspace.domain.QuotesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class QuotesViewModel @Inject constructor(
    private val quotesManager: QuotesManager,
    @IoDispatcher val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _errorMessage = MutableSharedFlow<Unit>()
    val errorMessage = _errorMessage.asSharedFlow()

    private val _downloading = MutableStateFlow(false)
    val downloading = _downloading.asStateFlow()

    val quotes = quotesManager.quotes

    val quotesByCharacters: Flow<List<QuoteOrCharacter>> =
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
            _downloading.emit(true)
            runCatching {
                quotesManager.downloadQuotes()
            }.onFailure {
                Timber.e(it.message)
                _errorMessage.emit(Unit)
            }
            _downloading.emit(false)
        }
    }
}
