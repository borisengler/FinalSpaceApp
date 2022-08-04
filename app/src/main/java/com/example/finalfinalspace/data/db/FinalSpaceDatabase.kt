package com.example.finalfinalspace.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.finalfinalspace.data.db.models.CharInEpiInfo
import com.example.finalfinalspace.data.db.models.CharactersInfo
import com.example.finalfinalspace.data.db.models.EpisodesInfo
import com.example.finalfinalspace.data.db.models.QuotesInfo

@Database(
    entities = [QuotesInfo::class, CharactersInfo::class, EpisodesInfo::class, CharInEpiInfo::class],
    version = 12, exportSchema = false
)
abstract class FinalSpaceDatabase : RoomDatabase() {

    abstract fun quotesDao(): QuotesDAO

    abstract fun charactersDao(): CharactersDAO

    abstract fun episodesDao(): EpisodesDAO

    abstract fun charInEpiDao(): CharInEpiDAO
}
