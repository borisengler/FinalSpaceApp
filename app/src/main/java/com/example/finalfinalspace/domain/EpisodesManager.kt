package com.example.finalfinalspace.domain

import com.example.finalfinalspace.data.api.FinalSpaceAPI
import com.example.finalfinalspace.data.db.CharInEpiDAO
import com.example.finalfinalspace.data.db.EpisodesDAO
import com.example.finalfinalspace.data.db.models.CharInEpiInfo
import com.example.finalfinalspace.data.db.models.EpisodeWithCharactersInfo
import com.example.finalfinalspace.extensions.toDatabase
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class EpisodesManager @Inject constructor(
    private val episodesDAO: EpisodesDAO,
    private val charInEpiDAO: CharInEpiDAO,
    private val finalSpaceAPI: FinalSpaceAPI
) {

    val episodes = episodesDAO.getAllEpisodes().distinctUntilChanged()

    suspend fun fetchEpisodeWithCharacters(id: Int): EpisodeWithCharactersInfo? {
        return episodesDAO.fetchEpisodeWithCharacters(id)
    }

    suspend fun downloadEpisodes() {
        val episodesWithChars = finalSpaceAPI.getEpisodes()

        episodesDAO.insertAll(episodesWithChars.map { it.toDatabase() })

        charInEpiDAO.insertAll(
            episodesWithChars.map {
                it.characters.map { character -> CharInEpiInfo(it.id, character.split("/").last().toInt()) }
            }.flatten()
        )
    }
}
