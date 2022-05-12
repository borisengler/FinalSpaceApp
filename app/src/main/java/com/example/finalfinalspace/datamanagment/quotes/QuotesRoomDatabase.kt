package com.example.finalfinalspace.datamanagment.quotes

import android.content.Context
import androidx.room.*
import com.example.finalfinalspace.datamanagment.quotes.QuotesDAO
import com.example.finalfinalspace.datamanagment.quotes.QuotesInfo

@Database(entities = [QuotesInfo::class], version = 1, exportSchema = false)
abstract class QuotesRoomDatabase : RoomDatabase() {

    abstract fun quotesDao(): QuotesDAO

    companion object {
        private var INSTANCE: QuotesRoomDatabase? = null
        fun getDatabase(context: Context): QuotesRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    QuotesRoomDatabase::class.java,
                    "quotes_db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}