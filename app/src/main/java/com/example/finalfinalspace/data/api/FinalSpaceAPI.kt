package com.example.finalfinalspace.data.api

import com.example.finalfinalspace.data.api.models.EpisodesWithCharsInfo
import com.example.finalfinalspace.data.db.models.CharactersInfo
import com.example.finalfinalspace.data.db.models.QuotesInfo
import retrofit2.http.GET

interface FinalSpaceAPI {

    @GET("episode")
    suspend fun getEpisodes(): List<EpisodesWithCharsInfo>

    @GET("quote")
    suspend fun getQuotes(): List<QuotesInfo>

    @GET("character")
    suspend fun getCharacters(): List<CharactersInfo>
}
