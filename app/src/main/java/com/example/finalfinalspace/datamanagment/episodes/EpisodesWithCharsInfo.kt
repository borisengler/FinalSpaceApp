package com.example.finalfinalspace.datamanagment.episodes

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

data class EpisodesWithCharsInfo(
    val id: Int = 0,
    val name: String,
    @Json(name = "air_date") val airDate: String,
    val director: String,
    val writer: String,
    @Json(name = "img_url") val imageUrl: String,
    val characters: List<String>
)