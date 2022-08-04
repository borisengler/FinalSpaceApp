package com.example.finalfinalspace.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "characters")
data class CharactersInfo(
    @PrimaryKey
    @ColumnInfo(name = "characterId")
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @Json(name = "img_url")
    @ColumnInfo(name = "img_url")
    val imageUrl: String
) : QuoteOrCharacter {
    override val uid: String
        get() = id.toString() + name
    override val type: Int
        get() = 0
}
