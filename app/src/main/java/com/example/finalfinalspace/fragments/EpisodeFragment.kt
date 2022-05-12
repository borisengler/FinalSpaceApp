package com.example.finalfinalspace.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.finalfinalspace.R
import com.example.finalfinalspace.datamanagment.episodes.EpisodesInfo
import com.example.finalfinalspace.datamanagment.episodes.EpisodesRoomDatabase
import com.example.finalfinalspace.datamanagment.episodes.EpisodesViewModel
import com.example.finalfinalspace.datamanagment.episodes.EpisodesViewModelFactory


class EpisodeFragment  : Fragment() {

    private lateinit var ctx: Context
    private lateinit var episodeData: EpisodesInfo


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (container != null) {
            ctx = container.context
        }
        val episodeId: Int = arguments?.get("episodeId") as Int

        val view: View = inflater.inflate(R.layout.fragment_episode, container, false)

        val database = EpisodesRoomDatabase.getDatabase(ctx)
        val episodesDao = database.episodeDao()
        episodeData = EpisodesViewModelFactory(episodesDao).create(EpisodesViewModel::class.java).retrieveEpisode(episodeId)

        view.findViewById<TextView>(R.id.episodeName).text = episodeData.name
        view.findViewById<TextView>(R.id.episodeDate).text = episodeData.airDate
        view.findViewById<TextView>(R.id.episodeDirector).text = episodeData.director
        view.findViewById<TextView>(R.id.episodeWriter).text = episodeData.writer
        view.findViewById<TextView>(R.id.episodeCharacters).text = episodeData.writer
        view.findViewById<TextView>(R.id.episodeSrc).text = episodeData.imageUrl

        return view
    }


}