package com.example.finalfinalspace.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalfinalspace.R
import com.example.finalfinalspace.datamanagment.episodes.EpisodesInfo
import com.example.finalfinalspace.datamanagment.episodes.EpisodesRoomDatabase
import com.example.finalfinalspace.datamanagment.episodes.EpisodesViewModel
import com.example.finalfinalspace.datamanagment.episodes.EpisodesViewModelFactory
import com.example.finalfinalspace.fragments.adapters.EpisodesRWAdapter

class EpisodesFragment : Fragment() {

    lateinit var ctx: Context
    lateinit var episodesData: List<EpisodesInfo>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (container != null) {
            ctx = container.getContext()
        };
        // Get episodes data
        val database = EpisodesRoomDatabase.getDatabase(ctx)
        val episodesDao = database.episodeDao()
        episodesData = EpisodesViewModelFactory(episodesDao).create(EpisodesViewModel::class.java).retrieveAllEpisodes()

        // set the recyclerview
        val view: View = inflater.inflate(R.layout.fragment_episodes, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.episodes)
        val emptyView: TextView = view.findViewById(R.id.empty_quotes)
        recyclerView.layoutManager = LinearLayoutManager(ctx)
        recyclerView.adapter = EpisodesRWAdapter(ctx, episodesData)

        // set visibility of views
        if (episodesData.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
        else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
        return view
    }


}