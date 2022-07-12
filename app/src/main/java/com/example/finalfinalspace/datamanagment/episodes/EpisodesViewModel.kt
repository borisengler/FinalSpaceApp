package com.example.finalfinalspace.datamanagment.episodes

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.finalfinalspace.datamanagment.network.EpisodesManager
import com.example.finalfinalspace.datamanagment.network.QuotesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EpisodesViewModel @Inject constructor(private val episodesManager: EpisodesManager
) : ViewModel() {

    val episodes = episodesManager.episodes

    suspend fun retrieveEpisode(id: Int): EpisodesInfo {
        return episodesManager.fetchEpisode(id)
    }

}
