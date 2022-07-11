package com.example.finalfinalspace.datamanagment.charInEpi

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "char_in_episode")
class CharInEpiInfo(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @ColumnInfo(name = "episode_id")
    val episode_id: Int,
    @ColumnInfo(name = "character_id")
    val character_id: Int
)