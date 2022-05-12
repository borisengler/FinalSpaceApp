package com.example.finalfinalspace.datamanagment.episodes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "episodes")
data class EpisodesInfo(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "air_date")
    @Json(name = "air_date") val airDate: String,
    @ColumnInfo(name = "director")
    val director: String,
    @ColumnInfo(name = "writer")
    val writer: String,
    @ColumnInfo(name = "img_url")
    @Json(name = "img_url") val imageUrl: String,
//    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
//    val image: Bitmap
)

