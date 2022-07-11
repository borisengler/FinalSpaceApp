package com.example.finalfinalspace.workers

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.content.ContextWrapper
import android.database.Cursor
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.finalfinalspace.datamanagment.FinalSpaceAPIObject
import com.example.finalfinalspace.datamanagment.charInEpi.CharInEpiInfo
import com.example.finalfinalspace.datamanagment.charInEpi.CharInEpiRoomDatabase
import com.example.finalfinalspace.datamanagment.characters.CharactersRoomDatabase
import com.example.finalfinalspace.datamanagment.episodes.EpisodesInfo
import com.example.finalfinalspace.datamanagment.episodes.EpisodesRoomDatabase
import com.example.finalfinalspace.datamanagment.episodes.EpisodesWithCharsInfo
import com.example.finalfinalspace.datamanagment.quotes.QuotesDAO
import com.example.finalfinalspace.datamanagment.quotes.QuotesRoomDatabase
import retrofit2.Call
import okhttp3.ResponseBody
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedInputStream
import java.io.File
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.nio.file.Files
import java.nio.file.Paths
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.io.path.exists
import kotlin.system.exitProcess

@Singleton
class SyncDataWorker(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {

    val context: Context = ctx
    @Inject lateinit var quotesDao: QuotesDAO

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("RestrictedApi")
    override suspend fun doWork(): Result {
        getCharacters()
        getEpisodes()
//        getQuotes()
        return Result.Success()
        // TODO zisti uspech a dat sem Toast.makeText()
    }

    private suspend fun getCharacters() {
        val database = CharactersRoomDatabase.getDatabase(context)
        val charactersDao = database.charactersDao()
        val characters = FinalSpaceAPIObject.retrofitService.getCharacters()
        charactersDao.insertAll(characters)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun getEpisodes() {
        val database = EpisodesRoomDatabase.getDatabase(context)
        val episodesDao = database.episodeDao()
        val episodesWithChars = FinalSpaceAPIObject.retrofitService.getEpisodes()

        val charInEpisodeDatabase = CharInEpiRoomDatabase.getDatabase(context)
        val charInEpisodeDao = charInEpisodeDatabase.charInEpiDao()
        var counter: Int = 0
        var path = ContextWrapper(context).getFilesDir().getAbsolutePath()
        path = "$path/images"
        if (!Paths.get(path).exists()) {
            Files.createDirectory(Paths.get(path))
        }
        for (episode: EpisodesWithCharsInfo in episodesWithChars) {

            val episode_id = episode.id
            val fileName = "image$episode_id.jpg"
            downloadImage(episode.imageUrl, File(path), fileName)
            episodesDao.insert(EpisodesInfo(episode.id,episode.name,episode.airDate,episode.director,episode.writer,episode.imageUrl))
            for (character: String in episode.characters) {
                val charId: Int = character.split("/").last().toInt()
                charInEpisodeDao.insert(CharInEpiInfo(++counter, episode.id, charId))
            }
        }
    }

//    private suspend fun getQuotes() {
//        val quotes = FinalSpaceAPIObject.retrofitService.getQuotes()
//        quotesDao.insertAll(quotes)
//    }

    @SuppressLint("Range")
    private fun downloadImage(url: String, directory: File, name: String) {
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val downloadUri = Uri.parse(url)

        val request = DownloadManager.Request(downloadUri).apply {
            setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle(name.toString())
                .setDescription("")
                .setDestinationInExternalFilesDir(
                    context,
                    "images",
                    name)
        }

        val downloadId = downloadManager.enqueue(request)
        val query = DownloadManager.Query().setFilterById(downloadId)
        Thread {
            val path = context.getExternalFilesDir(null).toString() + "/images/$name"
            if (File(path).exists()) {
                File(path).delete()
            }
            var downloading = true
            while (downloading) {
                val cursor: Cursor = downloadManager.query(query)
                cursor.moveToFirst()
                if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                    downloading = false
                }
                val status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                cursor.close()
            }
        }.start()
    }

}
