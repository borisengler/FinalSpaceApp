package com.example.finalfinalspace.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.finalfinalspace.R
import com.example.finalfinalspace.datamanagment.episodes.EpisodeWithCharactersInfo
import com.example.finalfinalspace.datamanagment.episodes.EpisodesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EpisodeFragment : Fragment() {

    private lateinit var ctx: Context
    private lateinit var episodeData: EpisodeWithCharactersInfo
    private val episodesVM: EpisodesViewModel by viewModels()
    private val scope = MainScope()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (container != null) {
            ctx = container.context
        }
        val episodeId: Int = arguments?.get("episodeId") as Int

        val view: View = inflater.inflate(R.layout.fragment_episode, container, false)
        // simple data
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                episodesVM.getEpisodeWithCharacters(episodeId).collectLatest {
                    view.findViewById<TextView>(R.id.episodeName)?.text = it.name
                    view.findViewById<TextView>(R.id.episodeDate).text =
                        String.format(resources.getString(R.string.episodeAirDate), it.airDate)
                    view.findViewById<TextView>(R.id.episodeDirector).text =
                        String.format(resources.getString(R.string.episodeDirector), it.director)
                    view.findViewById<TextView>(R.id.episodeWriter).text =
                        String.format(resources.getString(R.string.episodeWriter), it.writer)
                    view.findViewById<TextView>(R.id.episodeCharacters).text =
                        it.characters.joinToString(separator = ", ") { it.name }
                }
            }
        }

        return view
    }

}
