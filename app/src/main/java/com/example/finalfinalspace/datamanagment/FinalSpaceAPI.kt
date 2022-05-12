package com.example.finalfinalspace.datamanagment

import com.example.finalfinalspace.datamanagment.episodes.EpisodesInfo
import com.example.finalfinalspace.datamanagment.quotes.QuotesInfo
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL =
    "https://finalspaceapi.com/api/v0/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface FinalSpaceAPI {
    /**
     * Returns a [List] of [EpisodesInfo] and this method can be called from a Coroutine.
     * The @GET annotation indicates that the "episode" endpoint will be requested with the GET
     * HTTP method
     */
    @GET("episode")
    suspend fun getEpisodes() : List<EpisodesInfo>

    @GET("quote")
    suspend fun getQuotes() : List<QuotesInfo>
}

object FinalSpaceAPIObject {
    val retrofitService: FinalSpaceAPI by lazy { retrofit.create(FinalSpaceAPI::class.java) }
}
