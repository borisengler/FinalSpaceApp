package com.example.finalfinalspace.ui.quotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalfinalspace.data.db.models.QuoteOrCharacter
import com.example.finalfinalspace.di.qualifiers.IoDispatcher
import com.example.finalfinalspace.di.qualifiers.MainDispatcher
import com.example.finalfinalspace.domain.QuotesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class QuotesViewModel @Inject constructor(
    private val quotesManager: QuotesManager,
    @MainDispatcher val mainDispatcher: CoroutineDispatcher,
    @IoDispatcher val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _errorMessage = MutableSharedFlow<Unit>()
    val errorMessage = _errorMessage.asSharedFlow()

    private val _downloading = MutableStateFlow(false)
    val downloading = _downloading.asStateFlow()

    val filter = MutableSharedFlow<String>()

    val quotes = filter.flatMapLatest { filteredText ->
        quotesManager.getFilteredQuotes(filteredText).map { characterWithQuotes ->
            val items = mutableListOf<QuoteOrCharacter>()
            characterWithQuotes.keys.sortedBy { it.name }.forEach {
                items += it
                characterWithQuotes[it]?.let { quotesList -> items += quotesList }
            }
            return@map items
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    init {
        viewModelScope.launch(mainDispatcher) {
            filter.emit("")
        }
    }

    fun downloadData() {
        viewModelScope.launch(mainDispatcher) {
            _downloading.emit(true)
            withContext(ioDispatcher) {
                runCatching {
                    quotesManager.downloadQuotes()
                }.onFailure {
                    Timber.e(it)
                    _errorMessage.emit(Unit)
                }
                _downloading.emit(false)
            }
        }
    }
}
