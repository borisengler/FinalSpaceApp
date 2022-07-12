package com.example.finalfinalspace.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.finalfinalspace.R
import com.example.finalfinalspace.datamanagment.charInEpi.CharInEpiViewModel
import com.example.finalfinalspace.datamanagment.characters.CharactersViewModel
import com.example.finalfinalspace.datamanagment.episodes.EpisodesInfo
import com.example.finalfinalspace.datamanagment.episodes.EpisodesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EpisodeFragment : Fragment() {

    private lateinit var ctx: Context
    private lateinit var episodeData: EpisodesInfo
    private val characterVM: CharactersViewModel by viewModels()
    private val episodesVM: EpisodesViewModel by viewModels()
    private val charInEpiVM: CharInEpiViewModel by viewModels()
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
        scope.launch {
            episodeData = episodesVM.retrieveEpisode(episodeId)
            view.findViewById<TextView>(R.id.episodeName).text = episodeData.name
            view.findViewById<TextView>(R.id.episodeDate).text =
                String.format(resources.getString(R.string.episodeAirDate), episodeData.airDate)
            view.findViewById<TextView>(R.id.episodeDirector).text =
                String.format(resources.getString(R.string.episodeDirector), episodeData.director)
            view.findViewById<TextView>(R.id.episodeWriter).text =
                String.format(resources.getString(R.string.episodeWriter), episodeData.writer)
        }

        // characters
        scope.launch {
            val charactersInEpi = charInEpiVM.getCharactersInEpisode(episodeId)
            val characters: List<Int> = charactersInEpi.map { it.character_id }
            view.findViewById<TextView>(R.id.episodeCharacters).text =
                characterVM.retrieveCharacters(characters).joinToString(separator = ", ") { it.name }
        }
        return view
    }

}
