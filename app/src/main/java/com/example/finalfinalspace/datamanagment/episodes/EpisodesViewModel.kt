package com.example.finalfinalspace.datamanagment.episodes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalfinalspace.datamanagment.characters.CharactersManager
import com.example.finalfinalspace.di.qualifiers.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodesViewModel @Inject constructor(
    private val episodesManager: EpisodesManager,
    private val charactersManager: CharactersManager,
    @IoDispatcher var dispatcher: CoroutineDispatcher
) : ViewModel() {

    init {
        viewModelScope.launch(dispatcher) {
            charactersManager.downloadCharacters()
            episodesManager.downloadEpisodes()
        }
    }

    val episodes = episodesManager.episodes

    private val _episodeWithCharacters = MutableStateFlow<EpisodeWithCharactersInfo?>(null)

    val episodeWithCharacters = _episodeWithCharacters.asStateFlow()

    fun loadEpisodeWithCharacters(id: Int) {
        viewModelScope.launch {
            _episodeWithCharacters.emit(episodesManager.fetchEpisodeWithCharacters(id))
        }
    }
}
