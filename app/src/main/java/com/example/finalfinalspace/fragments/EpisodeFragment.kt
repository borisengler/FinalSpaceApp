package com.example.finalfinalspace.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.finalfinalspace.R
import com.example.finalfinalspace.databinding.FragmentEpisodeBinding
import com.example.finalfinalspace.datamanagment.episodes.EpisodesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EpisodeFragment : Fragment() {

    private val episodesVM: EpisodesViewModel by viewModels()

    lateinit var binding: FragmentEpisodeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val episodeId: Int = arguments?.get("episodeId") as Int

//        val view: View = inflater.inflate(R.layout.fragment_episode, container, false)

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_episode, container, false
        )

        binding.lifecycleOwner = viewLifecycleOwner

        binding.viewModel = episodesVM
        episodesVM.loadEpisodeWithCharacters(episodeId)

        return binding.root
    }

}
