package com.example.finalfinalspace.datamanagment.network

import androidx.lifecycle.distinctUntilChanged
import com.example.finalfinalspace.datamanagment.FinalSpaceAPI
import com.example.finalfinalspace.datamanagment.charInEpi.CharInEpiDAO
import com.example.finalfinalspace.datamanagment.charInEpi.CharInEpiInfo
import com.example.finalfinalspace.datamanagment.episodes.EpisodesWithCharsInfo
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class CharInEpiManager @Inject constructor(
    private val charInEpiDAO: CharInEpiDAO,
    private val finalSpaceAPI: FinalSpaceAPI
) {

    val charsInEpi = charInEpiDAO.getCharactersInEpisodes().distinctUntilChanged()

    suspend fun fetchCharactersInEpisode(episodeId: Int): List<CharInEpiInfo> {
        return charInEpiDAO.fetchCharactersInEpisode(episodeId)
    }

    suspend fun downloadCharactersInEpisodes() {
        val episodesWithChars = finalSpaceAPI.getEpisodes()
        var counter = 0
        for (episode: EpisodesWithCharsInfo in episodesWithChars) {

            for (character: String in episode.characters) {
                val charId: Int = character.split("/").last().toInt()
                charInEpiDAO.insert(CharInEpiInfo(++counter, episode.id, charId))
            }
        }
    }
}