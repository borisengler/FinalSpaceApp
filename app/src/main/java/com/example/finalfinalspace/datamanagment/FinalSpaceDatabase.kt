package com.example.finalfinalspace.datamanagment

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.finalfinalspace.datamanagment.charInEpi.CharInEpiDAO
import com.example.finalfinalspace.datamanagment.charInEpi.CharInEpiInfo
import com.example.finalfinalspace.datamanagment.characters.CharactersDAO
import com.example.finalfinalspace.datamanagment.characters.CharactersInfo
import com.example.finalfinalspace.datamanagment.episodes.EpisodesDAO
import com.example.finalfinalspace.datamanagment.episodes.EpisodesInfo
import com.example.finalfinalspace.datamanagment.quotes.QuotesDAO
import com.example.finalfinalspace.datamanagment.quotes.QuotesInfo

@Database(
    entities = [QuotesInfo::class, CharactersInfo::class, EpisodesInfo::class, CharInEpiInfo::class],
    version = 2, exportSchema = false
)
abstract class FinalSpaceDatabase : RoomDatabase() {

    abstract fun quotesDao(): QuotesDAO

    abstract fun charactersDao(): CharactersDAO

    abstract fun episodesDao(): EpisodesDAO

    abstract fun charInEpiDao(): CharInEpiDAO

}