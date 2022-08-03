package com.example.finalfinalspace.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "episodes")
data class EpisodesInfo(
    @PrimaryKey(autoGenerate = false)
    val episodeId: Int = 0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "air_date")
    val airDate: String,
    @ColumnInfo(name = "director")
    val director: String,
    @ColumnInfo(name = "writer")
    val writer: String,
    @ColumnInfo(name = "img_url")
    val imageUrl: String
)
