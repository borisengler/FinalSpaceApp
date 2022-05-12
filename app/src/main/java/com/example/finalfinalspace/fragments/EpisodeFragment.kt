package com.example.finalfinalspace.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalfinalspace.R
import com.example.finalfinalspace.datamanagment.episodes.EpisodesInfo
import com.example.finalfinalspace.datamanagment.quotes.QuotesInfo
import com.example.finalfinalspace.datamanagment.quotes.QuotesRoomDatabase
import com.example.finalfinalspace.datamanagment.quotes.QuotesViewModel
import com.example.finalfinalspace.datamanagment.quotes.QuotesViewModelFactory
import com.example.finalfinalspace.fragments.adapters.QuotesRWAdapter


class EpisodeFragment  : Fragment() {

    lateinit var ctx: Context
    lateinit var episodeData: EpisodesInfo


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (container != null) {
            ctx = container.getContext()
        };
        var episodeIndex: Int = arguments?.get("episodeIndex") as Int
        Log.d("EpisodeIndex", episodeIndex.toString())
        val view: View = inflater.inflate(R.layout.fragment_episode, container, false)
        return view
    }


}