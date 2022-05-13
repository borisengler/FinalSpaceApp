package com.example.finalfinalspace.datamanagment.charInEpi

import android.content.Context
import androidx.room.*

@Database(entities = [CharInEpiInfo::class], version = 1, exportSchema = false)
abstract class CharInEpiRoomDatabase : RoomDatabase() {

    abstract fun charInEpiDao(): CharInEpiDAO

    companion object {
        private var INSTANCE: CharInEpiRoomDatabase? = null
        fun getDatabase(context: Context): CharInEpiRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CharInEpiRoomDatabase::class.java,
                    "char_in_episode")
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