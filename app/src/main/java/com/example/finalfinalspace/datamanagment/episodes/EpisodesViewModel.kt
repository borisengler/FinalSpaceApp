package com.example.finalfinalspace.datamanagment.episodes

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalfinalspace.datamanagment.characters.CharactersManager
import com.example.finalfinalspace.di.qualifiers.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class EpisodesViewModel @Inject constructor(
    private val episodesManager: EpisodesManager,
    private val charactersManager: CharactersManager,
    @IoDispatcher val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _errorMessage = MutableSharedFlow<Unit>()
    val errorMessage = _errorMessage.asSharedFlow()

    init {
        downloadData()
    }

    val episodes = episodesManager.episodes

    private val _episodeWithCharacters = MutableStateFlow<EpisodeWithCharactersInfo?>(null)

    val episodeWithCharacters = _episodeWithCharacters.asStateFlow()

    fun loadEpisodeWithCharacters(id: Int) {
        viewModelScope.launch {
            _episodeWithCharacters.emit(episodesManager.fetchEpisodeWithCharacters(id))
        }
    }

    fun downloadData() {
        viewModelScope.launch(ioDispatcher) {
            runCatching {
                charactersManager.downloadCharacters()
                episodesManager.downloadEpisodes()
            }.onFailure {
                _errorMessage.emit(Unit)
            }
        }
    }
}
