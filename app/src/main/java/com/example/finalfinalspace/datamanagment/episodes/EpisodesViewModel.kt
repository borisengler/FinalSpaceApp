package com.example.finalfinalspace.datamanagment.episodes

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class EpisodesViewModel @Inject constructor(
    private val episodesManager: EpisodesManager,
) : ViewModel() {

    val episodes = episodesManager.episodes

    fun getEpisodeWithCharacters(id: Int): Flow<EpisodeWithCharactersInfo> {
        return episodesManager.getEpisodeWithCharacters(id)
    }

}
