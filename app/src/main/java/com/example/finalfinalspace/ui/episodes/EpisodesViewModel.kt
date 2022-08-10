package com.example.finalfinalspace.ui.episodes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalfinalspace.data.db.models.EpisodeWithCharactersInfo
import com.example.finalfinalspace.di.qualifiers.IoDispatcher
import com.example.finalfinalspace.di.qualifiers.MainDispatcher
import com.example.finalfinalspace.domain.CharactersManager
import com.example.finalfinalspace.domain.EpisodesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EpisodesViewModel @Inject constructor(
    private val episodesManager: EpisodesManager,
    private val charactersManager: CharactersManager,
    @MainDispatcher val mainDispatcher: CoroutineDispatcher,
    @IoDispatcher val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _errorMessage = MutableSharedFlow<Unit>()
    val errorMessage = _errorMessage.asSharedFlow()

    private val _downloading = MutableStateFlow(false)
    val downloading = _downloading.asStateFlow()

    val episodes = episodesManager.episodes

    private val _episodeWithCharacters = MutableStateFlow<EpisodeWithCharactersInfo?>(null)

    val episodeWithCharacters = _episodeWithCharacters.asStateFlow()

    fun loadEpisodeWithCharacters(id: Int) {
        viewModelScope.launch(ioDispatcher) {
            _episodeWithCharacters.emit(episodesManager.fetchEpisodeWithCharacters(id))
        }
    }

    fun downloadData() {
        viewModelScope.launch(mainDispatcher) {
            _downloading.emit(true)
            withContext(ioDispatcher) {
                runCatching {
                    charactersManager.downloadCharacters()
                    episodesManager.downloadEpisodes()
                }.onFailure {
                    Timber.e(it)
                    _errorMessage.emit(Unit)
                }
                _downloading.emit(false)
            }
        }
    }
}
