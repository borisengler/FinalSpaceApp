package com.example.finalfinalspace.di

import android.content.Context
import androidx.room.Room
import com.example.finalfinalspace.datamanagment.FinalSpaceDatabase
import com.example.finalfinalspace.datamanagment.charInEpi.CharInEpiDAO
import com.example.finalfinalspace.datamanagment.characters.CharactersDAO
import com.example.finalfinalspace.datamanagment.episodes.EpisodesDAO
import com.example.finalfinalspace.datamanagment.quotes.QuotesDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object QuotesDBModule {
    @Provides
    fun provideQuotesDao(database: FinalSpaceDatabase): QuotesDAO {
        return database.quotesDao()
    }

    @Provides
    fun provideCharactersDao(database: FinalSpaceDatabase): CharactersDAO {
        return database.charactersDao()
    }

    @Provides
    fun provideEpisodesDao(database: FinalSpaceDatabase): EpisodesDAO {
        return database.episodesDao()
    }

    @Provides
    fun provideCharInEpiDao(database: FinalSpaceDatabase): CharInEpiDAO {
        return database.charInEpiDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): FinalSpaceDatabase {
        return Room.databaseBuilder(
            appContext,
            FinalSpaceDatabase::class.java,
            "final_space_db"
        ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
    }

}