package com.example.finalfinalspace.datamanagment.characters

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class CharactersInfo(
    @PrimaryKey
    @ColumnInfo(name = "characterId")
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String
)
