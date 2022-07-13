package com.example.finalfinalspace.datamanagment.network

import com.example.finalfinalspace.datamanagment.charInEpi.CharInEpiManager
import com.example.finalfinalspace.datamanagment.characters.CharactersManager
import com.example.finalfinalspace.datamanagment.episodes.EpisodesManager
import com.example.finalfinalspace.datamanagment.quotes.QuotesManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FinalSpaceDownloadManager @Inject constructor(
    private val quotesManager: QuotesManager,
    private val charactersManager: CharactersManager,
    private val charInEpiManager: CharInEpiManager,
    private val episodesManager: EpisodesManager
) {
    suspend fun downloadAllData() {
        episodesManager.downloadEpisodes()
        quotesManager.downloadQuotes()
        charactersManager.downloadCharacters()
        charInEpiManager.downloadCharactersInEpisodes()
    }
}