package com.example.finalfinalspace.datamanagment

import com.example.finalfinalspace.datamanagment.characters.CharactersInfo
import com.example.finalfinalspace.datamanagment.episodes.EpisodesWithCharsInfo
import com.example.finalfinalspace.datamanagment.quotes.QuotesInfo
import retrofit2.http.GET

interface FinalSpaceAPI {

    @GET("episode")
    suspend fun getEpisodes(): List<EpisodesWithCharsInfo>

    @GET("quote")
    suspend fun getQuotes(): List<QuotesInfo>

    @GET("character")
    suspend fun getCharacters(): List<CharactersInfo>
}
