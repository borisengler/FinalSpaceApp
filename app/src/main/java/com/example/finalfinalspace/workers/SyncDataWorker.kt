package com.example.finalfinalspace.workers

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Looper
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.finalfinalspace.MainActivity
import com.example.finalfinalspace.datamanagment.FinalSpaceAPIObject
import com.example.finalfinalspace.datamanagment.charInEpi.CharInEpiInfo
import com.example.finalfinalspace.datamanagment.charInEpi.CharInEpiRoomDatabase
import com.example.finalfinalspace.datamanagment.characters.CharactersRoomDatabase
import com.example.finalfinalspace.datamanagment.episodes.EpisodesDAO
import com.example.finalfinalspace.datamanagment.episodes.EpisodesInfo
import com.example.finalfinalspace.datamanagment.episodes.EpisodesRoomDatabase
import com.example.finalfinalspace.datamanagment.episodes.EpisodesWithCharsInfo
import com.example.finalfinalspace.datamanagment.images.ImageRetrofitClient
import com.example.finalfinalspace.datamanagment.quotes.QuotesRoomDatabase
import retrofit2.Call
import okhttp3.ResponseBody
import retrofit2.Callback
import retrofit2.Response

class SyncDataWorker(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {

    val context: Context = ctx

    @SuppressLint("RestrictedApi")
    override suspend fun doWork(): Result {
        getCharacters()
        getEpisodes()
        getQuotes()
        return Result.Success()
        // TODO zisti uspech a dat sem Toast.makeText()
    }

    private suspend fun getCharacters() {
        val database = CharactersRoomDatabase.getDatabase(context)
        val charactersDao = database.charactersDao()
        val characters = FinalSpaceAPIObject.retrofitService.getCharacters()
        charactersDao.insertAll(characters)
    }

    private suspend fun getEpisodes() {
        val database = EpisodesRoomDatabase.getDatabase(context)
        val episodesDao = database.episodeDao()
        val episodesWithChars = FinalSpaceAPIObject.retrofitService.getEpisodes()

        val charInEpisodeDatabase = CharInEpiRoomDatabase.getDatabase(context)
        val charInEpisodeDao = charInEpisodeDatabase.charInEpiDao()
        var counter: Int = 0

        for (episode: EpisodesWithCharsInfo in episodesWithChars) {
            funDownloadImage(episode.imageUrl, episode, episodesDao)
            Log.d("after download", "idk")
            for (character: String in episode.characters) {
                val charId: Int = character.split("/").last().toInt()
                charInEpisodeDao.insert(CharInEpiInfo(++counter, episode.id, charId))
            }
        }
    }

    private suspend fun getQuotes() {
        val database = QuotesRoomDatabase.getDatabase(context)
        val quotesDao = database.quotesDao()
        val quotes = FinalSpaceAPIObject.retrofitService.getQuotes()
        quotesDao.insertAll(quotes)
    }

    private fun funDownloadImage(imageUrl: String, episode: EpisodesWithCharsInfo, episodesDao: EpisodesDAO) {
        Thread() {
            var bytes: ByteArray = byteArrayOf()
//            Looper.prepare()
            val progressDialog = ProgressDialog(context)
            progressDialog.setTitle("Image is downloading, please wait")
            progressDialog.show()


            val call: Call<ResponseBody> = ImageRetrofitClient.getClient.downloadImage(imageUrl)
            call.enqueue(object : Callback<ResponseBody> {

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    call.execute()
                    progressDialog.hide();
                    if (response.isSuccessful()) {
                        Log.d("response", "success")
                        bytes = response.body()!!.bytes()
                        episodesDao.insert(
                            EpisodesInfo(
                                episode.id,
                                episode.name,
                                episode.airDate,
                                episode.director,
                                episode.writer,
                                episode.imageUrl,
                                bytes
                            )
                        )
                        progressDialog.dismiss()
                    } else {
                        Log.d("respones", "unsuccesful")
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    progressDialog.dismiss()
                }
            })
//            call.execute()

        }
    }

}