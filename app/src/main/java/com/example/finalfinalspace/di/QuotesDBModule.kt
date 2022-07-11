package com.example.finalfinalspace.di

import android.content.Context
import androidx.room.Room
import com.example.finalfinalspace.datamanagment.quotes.QuotesDAO
import com.example.finalfinalspace.datamanagment.quotes.QuotesRoomDatabase
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
    fun provideQuotesDao(database: QuotesRoomDatabase): QuotesDAO {
        return database.quotesDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context) : QuotesRoomDatabase {
        return Room.databaseBuilder(
            appContext,
            QuotesRoomDatabase::class.java,
            "quotes.db"
        ).fallbackToDestructiveMigration().build()
    }

}