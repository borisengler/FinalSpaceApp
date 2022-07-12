package com.example.finalfinalspace.datamanagment.network

import android.util.Log
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