package com.example.finalfinalspace.datamanagment.charInEpi

import com.example.finalfinalspace.datamanagment.FinalSpaceAPI
import com.example.finalfinalspace.datamanagment.characters.CharactersInfo
import com.example.finalfinalspace.datamanagment.episodes.EpisodesWithCharsInfo
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class CharInEpiManager @Inject constructor(
    private val charInEpiDAO: CharInEpiDAO,
    private val finalSpaceAPI: FinalSpaceAPI
) {

    val charsInEpi = charInEpiDAO.getCharactersInEpisodes().distinctUntilChanged()

    suspend fun fetchCharactersInEpisode(episodeId: Int): List<CharactersInfo> {
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