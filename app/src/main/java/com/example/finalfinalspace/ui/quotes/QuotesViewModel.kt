package com.example.finalfinalspace.ui.quotes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalfinalspace.data.db.models.QuoteOrCharacter
import com.example.finalfinalspace.di.qualifiers.IoDispatcher
import com.example.finalfinalspace.di.qualifiers.MainDispatcher
import com.example.finalfinalspace.domain.CharactersManager
import com.example.finalfinalspace.domain.QuotesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.Locale
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

    var quotes: Flow<List<QuoteOrCharacter>> = flowOf(emptyList())

    init {
        viewModelScope.launch(mainDispatcher) {
            filter.emit("")
        }
    }

    fun getFilteredQuotes(filter: String = "") {
        quotes = quotesManager.getFilteredQuotes(filter).map { characterWithQuotes ->
            val items = mutableListOf<QuoteOrCharacter>()
            characterWithQuotes.keys.sortedBy { it.name }.forEach {
                items += it
                items += characterWithQuotes[it]!!
            }
            return@map items
        }.flowOn(ioDispatcher)
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
