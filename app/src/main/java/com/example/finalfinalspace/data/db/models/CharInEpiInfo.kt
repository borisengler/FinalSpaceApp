package com.example.finalfinalspace.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE

@Entity(
    primaryKeys = ["episodeId", "characterId"], tableName = "char_in_episode",
    foreignKeys = [
        ForeignKey(
            entity = EpisodesInfo::class,
            parentColumns = ["episodeId"],
            childColumns = ["episodeId"],
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = CharactersInfo::class,
            parentColumns = ["characterId"],
            childColumns = ["characterId"],
            onDelete = CASCADE
        )
    ]
)
data class CharInEpiInfo(
    @ColumnInfo(index = true)
    val episodeId: Int,
    @ColumnInfo(index = true)
    val characterId: Int
)
