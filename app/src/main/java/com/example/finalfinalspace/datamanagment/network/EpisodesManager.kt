package com.example.finalfinalspace.datamanagment.network

import androidx.lifecycle.distinctUntilChanged
import com.example.finalfinalspace.datamanagment.FinalSpaceAPI
import com.example.finalfinalspace.datamanagment.episodes.EpisodesDAO
import com.example.finalfinalspace.datamanagment.episodes.EpisodesInfo
import com.example.finalfinalspace.datamanagment.episodes.EpisodesWithCharsInfo
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class EpisodesManager @Inject constructor(
    private val episodesDAO: EpisodesDAO,
    private val finalSpaceAPI: FinalSpaceAPI
) {

    val episodes = episodesDAO.getAllEpisodes().distinctUntilChanged()

    suspend fun fetchEpisode(id: Int): EpisodesInfo {
        return episodesDAO.fetchEpisode(id)
    }

    suspend fun downloadEpisodes() {
        val episodesWithChars = finalSpaceAPI.getEpisodes()
        for (episode: EpisodesWithCharsInfo in episodesWithChars) {
            episodesDAO.insert(
                EpisodesInfo(
                    episode.id,
                    episode.name,
                    episode.airDate,
                    episode.director,
                    episode.writer,
                    episode.imageUrl
                )
            )
        }
    }
}