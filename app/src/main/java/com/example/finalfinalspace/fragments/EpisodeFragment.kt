package com.example.finalfinalspace.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
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
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEpisodeBinding.inflate(inflater, container, false)

        val episodeId = arguments?.get("episodeId") as? Int

        if (episodeId != null) {
            episodesVM.loadEpisodeWithCharacters(episodeId)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                episodesVM.episodeWithCharacters.collectLatest {
                    with(binding) {
                        episodeName.text = it?.episode?.name
                        episodeDate.text = getString(R.string.episodeAirDate, it?.episode?.airDate)
                        episodeDirector.text = getString(R.string.episodeDirector, it?.episode?.director)
                        episodeWriter.text = getString(R.string.episodeWriter, it?.episode?.writer)
                        episodeCharacters.text = it?.characters?.joinToString { it.name }
                        Glide.with(root).load(it?.episode?.imageUrl).into(episodeImage)
                    }
                }
            }
        }

        return binding.root
    }
}
