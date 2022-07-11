package com.example.finalfinalspace.fragments

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.content.ContextWrapper
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.finalfinalspace.R
import com.example.finalfinalspace.datamanagment.FinalSpaceAPIObject
import com.example.finalfinalspace.datamanagment.charInEpi.CharInEpiDAO
import com.example.finalfinalspace.datamanagment.charInEpi.CharInEpiInfo
import com.example.finalfinalspace.datamanagment.characters.CharactersDAO
import com.example.finalfinalspace.datamanagment.episodes.EpisodesDAO
import com.example.finalfinalspace.datamanagment.episodes.EpisodesInfo
import com.example.finalfinalspace.datamanagment.episodes.EpisodesWithCharsInfo
import com.example.finalfinalspace.datamanagment.quotes.QuotesDAO
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import javax.inject.Inject
import kotlin.io.path.exists

@AndroidEntryPoint
class NavigationFragment : Fragment(), View.OnClickListener {

    private lateinit var navController: NavController
    private lateinit var ctx: Context
//    TODO vsetky dao potrebujem len kvoli download buttonu, prerobit (asi na service)
    @Inject lateinit var quotesDao: QuotesDAO
    @Inject lateinit var charactersDao: CharactersDAO
    @Inject lateinit var episodesDao: EpisodesDAO
    @Inject lateinit var charInEpisodeDao: CharInEpiDAO
    private val scope = MainScope()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        if (container != null) {
            ctx = container.context
        }
        return inflater.inflate(R.layout.fragment_navigation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        view.findViewById<Button>(R.id.syncButton).setOnClickListener(this)
        view.findViewById<Button>(R.id.quotesButton).setOnClickListener(this)
        view.findViewById<Button>(R.id.episodesButton).setOnClickListener(this)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.syncButton -> syncData()
            R.id.quotesButton -> navController.navigate(R.id.action_navigationFragment_to_quotesFragment)
            R.id.episodesButton -> navController.navigate(R.id.action_navigationFragment_to_episodesFragment)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun syncData() {
        scope.launch {
//            TODO spravit asi service na downloader a vsetky dao injectnut
            DataDownloader(ctx, quotesDao, charactersDao, episodesDao, charInEpisodeDao).download()
        }
    }

}

//TODO move it elsewhere
class DataDownloader(
    ctx: Context,
    quotesDAO: QuotesDAO,
    charactersDAO: CharactersDAO,
    episodesDAO: EpisodesDAO,
    charInEpiDAO: CharInEpiDAO
) {
    private var quotesDao: QuotesDAO = quotesDAO
    private var charactersDao: CharactersDAO = charactersDAO
    private var episodesDao: EpisodesDAO = episodesDAO
    private var charInEpisodeDao: CharInEpiDAO = charInEpiDAO
    private var context: Context = ctx

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun download() {
        val quotes = FinalSpaceAPIObject.retrofitService.getQuotes()
        quotesDao.insertAll(quotes)
        val characters = FinalSpaceAPIObject.retrofitService.getCharacters()
        charactersDao.insertAll(characters)
        val episodesWithChars = FinalSpaceAPIObject.retrofitService.getEpisodes()
        var counter: Int = 0
        var path = ContextWrapper(context).getFilesDir().getAbsolutePath()
        path = "$path/images"
        if (!Paths.get(path).exists()) {
            Files.createDirectory(Paths.get(path))
        }
        for (episode: EpisodesWithCharsInfo in episodesWithChars) {

            val episode_id = episode.id
            val fileName = "image$episode_id.jpg"
//            downloadImage(episode.imageUrl, File(path), fileName)
            episodesDao.insert(
                EpisodesInfo(
                    episode.id,
                    episode.name,
                    episode.airDate,
                    episode.director,
                    episode.writer,
                    episode.imageUrl
                )
            )
            for (character: String in episode.characters) {
                val charId: Int = character.split("/").last().toInt()
                charInEpisodeDao.insert(CharInEpiInfo(++counter, episode.id, charId))
            }
        }
    }

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
                    name
                )
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