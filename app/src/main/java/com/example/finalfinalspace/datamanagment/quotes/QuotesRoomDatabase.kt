package com.example.finalfinalspace.datamanagment.quotes

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [QuotesInfo::class], version = 1, exportSchema = false)
abstract class QuotesRoomDatabase : RoomDatabase() {

    abstract fun quotesDao(): QuotesDAO

}