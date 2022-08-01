package com.example.finalfinalspace.datamanagment.network

import com.example.finalfinalspace.datamanagment.characters.CharactersManager
import com.example.finalfinalspace.datamanagment.episodes.EpisodesManager
import com.example.finalfinalspace.datamanagment.quotes.QuotesManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FinalSpaceDownloadManager @Inject constructor(
    private val quotesManager: QuotesManager,
    private val charactersManager: CharactersManager,
    private val episodesManager: EpisodesManager
) {
    suspend fun downloadAllData() {
        charactersManager.downloadCharacters()
        episodesManager.downloadEpisodes()
        quotesManager.downloadQuotes()
    }
}