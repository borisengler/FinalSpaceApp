package com.example.finalfinalspace.datamanagment.characters

import android.content.Context
import androidx.room.*

@Database(entities = [CharactersInfo::class], version = 1, exportSchema = false)
abstract class CharactersRoomDatabase : RoomDatabase() {

    abstract fun charactersDao(): CharactersDAO

    companion object {
        private var INSTANCE: CharactersRoomDatabase? = null
        fun getDatabase(context: Context): CharactersRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CharactersRoomDatabase::class.java,
                    "characters")
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