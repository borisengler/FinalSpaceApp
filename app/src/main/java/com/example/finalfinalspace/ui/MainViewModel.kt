package com.example.finalfinalspace.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalfinalspace.data.prefs.SettingsStorage
import com.example.finalfinalspace.di.qualifiers.IoDispatcher
import com.example.finalfinalspace.domain.CharactersManager
import com.example.finalfinalspace.domain.EpisodesManager
import com.example.finalfinalspace.domain.QuotesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val episodesManager: EpisodesManager,
    private val charactersManager: CharactersManager,
    private val quotesManager: QuotesManager,
    private val settingsStorage: SettingsStorage,
    @IoDispatcher val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    init {
        if (autoSync) {
            downloadData()
        }
    }

    private val _downloadStatus = MutableSharedFlow<Boolean>()
    val downloadStatus = _downloadStatus.asSharedFlow()

    private val autoSync get() = settingsStorage.getAutoSync()

    fun downloadData() {
        viewModelScope.launch(ioDispatcher) {
            runCatching {
                charactersManager.downloadCharacters()
                episodesManager.downloadEpisodes()
                quotesManager.downloadQuotes()
            }.onFailure {
                Timber.e(it.message)
                _downloadStatus.emit(false)
            }.onSuccess {
                _downloadStatus.emit(true)
            }
        }
    }
}
