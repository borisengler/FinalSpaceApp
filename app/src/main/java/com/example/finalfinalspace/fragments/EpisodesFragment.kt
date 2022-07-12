package com.example.finalfinalspace.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalfinalspace.R
import com.example.finalfinalspace.datamanagment.episodes.EpisodesViewModel
import com.example.finalfinalspace.fragments.adapters.EpisodesRWAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EpisodesFragment : Fragment() {

    private lateinit var ctx: Context
    private lateinit var navController: NavController
    private val episodesVM: EpisodesViewModel by viewModels()
    @Inject lateinit var episodesRWAdapter: EpisodesRWAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (container != null) {
            ctx = container.context
        }

        // Get episodes data
        episodesVM.episodes.observe(viewLifecycleOwner) {
            episodesRWAdapter.submitList(it)
        }

        // set the recyclerview
        val view: View = inflater.inflate(R.layout.fragment_episodes, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.episodes)
        recyclerView.layoutManager = LinearLayoutManager(ctx)
        recyclerView.adapter = episodesRWAdapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        episodesRWAdapter.setOnClickListener(object : EpisodesRWAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val bundle: Bundle = bundleOf("episodeId" to position + 1)
                navController.navigate(R.id.action_episodesFragment_to_episodeFragment, bundle)
            }

        })

    }

}