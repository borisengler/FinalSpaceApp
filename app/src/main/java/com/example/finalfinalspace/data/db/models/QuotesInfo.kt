package com.example.finalfinalspace.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quotes_db")
data class QuotesInfo(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    @ColumnInfo(name = "quote")
    val quote: String,
    @ColumnInfo(name = "by_who")
    val by: String,
) : QuoteOrCharacter {
    override val uid: String
        get() = id.toString() + quote
    override val type: Int
        get() = 1
}
