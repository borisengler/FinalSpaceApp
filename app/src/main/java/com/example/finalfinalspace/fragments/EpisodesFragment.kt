package com.example.finalfinalspace.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalfinalspace.R
import com.example.finalfinalspace.databinding.FragmentEpisodesBinding
import com.example.finalfinalspace.datamanagment.episodes.EpisodesViewModel
import com.example.finalfinalspace.fragments.adapters.EpisodesRWAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class EpisodesFragment : Fragment() {

    private lateinit var navController: NavController
    private val episodesVM: EpisodesViewModel by viewModels()
    @Inject lateinit var episodesRWAdapter: EpisodesRWAdapter
    private lateinit var binding: FragmentEpisodesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEpisodesBinding.inflate(inflater, container, false)

        // Get episodes data
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    episodesVM.episodes.collectLatest {
                        if (it.isNotEmpty()) {
                            binding.episodesRecyclerView.visibility = View.VISIBLE
                            binding.empty.visibility = View.GONE
                            episodesRWAdapter.submitList(it)
                        } else {
                            binding.episodesRecyclerView.visibility = View.GONE
                            binding.empty.visibility = View.VISIBLE
                        }
                    }
                }
                launch {
                    episodesVM.errorMessage.collectLatest {
                        Toast.makeText(
                            context,
                            "Unable to sync data",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        // set the recyclerview
        val recyclerView: RecyclerView = binding.episodesRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = episodesRWAdapter

        // set refresh swipe
        val swipeRefreshLayout = binding.episodesRefresh
        swipeRefreshLayout.setOnRefreshListener {
            episodesVM.downloadData()
            swipeRefreshLayout.isRefreshing = false
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        episodesRWAdapter.setOnClickListener(object : EpisodesRWAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val bundle: Bundle = bundleOf("episodeId" to position + 1)
                navController.navigate(R.id.action_episodesFragment2_to_episodeFragment2, bundle)
            }
        })
    }
}
