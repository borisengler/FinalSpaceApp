package com.example.finalfinalspace.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalfinalspace.R
import com.example.finalfinalspace.datamanagment.episodes.EpisodesInfo
import com.example.finalfinalspace.datamanagment.episodes.EpisodesRoomDatabase
import com.example.finalfinalspace.datamanagment.episodes.EpisodesViewModel
import com.example.finalfinalspace.datamanagment.episodes.EpisodesViewModelFactory
import com.example.finalfinalspace.fragments.adapters.EpisodesRWAdapter

class EpisodesFragment : Fragment() {

    private lateinit var ctx: Context
    private lateinit var episodesData: List<EpisodesInfo>
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (container != null) {
            ctx = container.context
        }

        // Get episodes data
        val database = EpisodesRoomDatabase.getDatabase(ctx)
        val episodesDao = database.episodeDao()
        episodesData = EpisodesViewModelFactory(episodesDao).create(EpisodesViewModel::class.java).retrieveAllEpisodes()

        // set the recyclerview
        val view: View = inflater.inflate(R.layout.fragment_episodes, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.episodes)
        val emptyView: TextView = view.findViewById(R.id.empty_quotes)
        recyclerView.layoutManager = LinearLayoutManager(ctx)
        val adapter = EpisodesRWAdapter(ctx, episodesData)
        recyclerView.adapter = adapter

        // set visibility of views
        if (episodesData.isEmpty()) {
            recyclerView.visibility = View.GONE
            emptyView.visibility = View.VISIBLE
        }
        else {
            recyclerView.visibility = View.VISIBLE
            emptyView.visibility = View.GONE
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        val recyclerView: RecyclerView = view.findViewById(R.id.episodes)
        val adapter: EpisodesRWAdapter = recyclerView.adapter as EpisodesRWAdapter
        adapter.setOnClickListener(object: EpisodesRWAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val bundle: Bundle = bundleOf("episodeId" to position+1)
                navController.navigate(R.id.action_episodesFragment_to_episodeFragment, bundle)
            }

        })
        recyclerView.adapter = adapter

    }

}