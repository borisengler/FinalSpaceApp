package com.example.finalfinalspace.datamanagment.episodes

import com.example.finalfinalspace.datamanagment.FinalSpaceAPI
import com.example.finalfinalspace.datamanagment.charInEpi.CharInEpiDAO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class EpisodesManager @Inject constructor(
    private val episodesDAO: EpisodesDAO,
    private val charInEpiDAO: CharInEpiDAO,
    private val finalSpaceAPI: FinalSpaceAPI
) {

    val episodes = episodesDAO.getAllEpisodes().distinctUntilChanged()

    fun getEpisodeWithCharacters(id: Int): Flow<EpisodeWithCharactersInfo> {
        return episodesDAO.getEpisode(id).combine(charInEpiDAO.getCharacterIdsInEpisodes(id)) { episode, characters ->
            EpisodeWithCharactersInfo(
                episode.id,
                episode.name,
                episode.airDate,
                episode.director,
                episode.writer,
                episode.imageUrl,
                characters
            )
        }
    }

    suspend fun fetchEpisodeWithCharacters(id: Int): EpisodeWithCharactersInfo {
        val episode = episodesDAO.fetchEpisode(id)
        val characters = charInEpiDAO.fetchCharactersInEpisode(id)
        return EpisodeWithCharactersInfo(
            episode.id,
            episode.name,
            episode.airDate,
            episode.director,
            episode.writer,
            episode.imageUrl,
            characters)

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