package com.example.finalfinalspace.datamanagment.episodes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodesViewModel @Inject constructor(
    private val episodesManager: EpisodesManager,
) : ViewModel() {

    val episodes = episodesManager.episodes

    //    todo private values
    private val _episodeWithCharacters = MutableStateFlow<EpisodeWithCharactersInfo?>(null)
    val episodeWithCharacters = _episodeWithCharacters.asStateFlow()
    val characters = episodeWithCharacters.map { it?.characters?.joinToString { it.name } }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        ""
    )

    fun loadEpisodeWithCharacters(id: Int) {
        viewModelScope.launch {
            _episodeWithCharacters.emit(episodesManager.fetchEpisodeWithCharacters(id))
        }

    }

}
