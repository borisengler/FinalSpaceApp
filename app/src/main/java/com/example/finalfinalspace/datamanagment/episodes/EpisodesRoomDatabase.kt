package com.example.finalfinalspace.datamanagment.episodes

import android.content.Context
import androidx.room.*

@Database(entities = [EpisodesInfo::class], version = 1, exportSchema = false)
abstract class EpisodesRoomDatabase : RoomDatabase() {

    abstract fun episodeDao(): EpisodesDAO

    companion object {
        private var INSTANCE: EpisodesRoomDatabase? = null
        fun getDatabase(context: Context): EpisodesRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EpisodesRoomDatabase::class.java,
                    "episodes")
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