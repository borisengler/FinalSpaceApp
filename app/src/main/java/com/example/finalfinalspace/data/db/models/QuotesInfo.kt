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
)
