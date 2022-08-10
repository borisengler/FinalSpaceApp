package com.example.finalfinalspace.ui.episodes

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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EpisodeFragment : Fragment() {

    private val episodesVM: EpisodesViewModel by viewModels()

    private var binding: FragmentEpisodeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEpisodeBinding.inflate(inflater, container, false)

        val episodeId = arguments?.get("episodeId") as? Int
        if (episodeId != null) {
            episodesVM.loadEpisodeWithCharacters(episodeId)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                episodesVM.episodeWithCharacters.collectLatest {
                    with(binding) {
                        this?.episodeName?.text = it?.episode?.name
                        this?.episodeDate?.text = getString(R.string.episodeAirDate, it?.episode?.airDate)
                        this?.episodeDirector?.text = getString(R.string.episodeDirector, it?.episode?.director)
                        this?.episodeWriter?.text = getString(R.string.episodeWriter, it?.episode?.writer)
                        this?.episodeCharacters?.text = it?.characters?.joinToString { it.name }
                        this?.episodeImage?.let { it1 -> this.root.let {
                            it2 -> Glide.with(it2).load(it?.episode?.imageUrl).into(it1) }
                        }
                    }
                }
            }
        }
        return binding?.root
    }
}
