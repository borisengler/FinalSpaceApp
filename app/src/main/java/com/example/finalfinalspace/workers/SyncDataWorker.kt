package com.example.finalfinalspace.workers

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.finalfinalspace.datamanagment.FinalSpaceAPIObject
import com.example.finalfinalspace.datamanagment.episodes.EpisodesRoomDatabase
import com.example.finalfinalspace.datamanagment.quotes.QuotesRoomDatabase

class SyncDataWorker(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {

    val context: Context = ctx

    @SuppressLint("RestrictedApi")
    override suspend fun doWork(): Result {
        getEpisodes()
        getQuotes()
        return Result.Success()
        // TODO zisti uspech a dat sem Toast.makeText()
    }

    private suspend fun getEpisodes() {
        val database = EpisodesRoomDatabase.getDatabase(context)
        val episodesDao = database.episodeDao()
        val episodes = FinalSpaceAPIObject.retrofitService.getEpisodes()
        episodesDao.insertAll(episodes)
    }

    private suspend fun getQuotes() {
        val database = QuotesRoomDatabase.getDatabase(context)
        val quotesDao = database.quotesDao()
        val quotes = FinalSpaceAPIObject.retrofitService.getQuotes()
        quotesDao.insertAll(quotes)
    }

}