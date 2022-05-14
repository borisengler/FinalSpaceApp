package com.example.finalfinalspace.fragments

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.finalfinalspace.R
import com.example.finalfinalspace.datamanagment.charInEpi.CharInEpiInfo
import com.example.finalfinalspace.datamanagment.charInEpi.CharInEpiRoomDatabase
import com.example.finalfinalspace.datamanagment.charInEpi.CharInEpiViewModel
import com.example.finalfinalspace.datamanagment.charInEpi.CharInEpiViewModelFactory
import com.example.finalfinalspace.datamanagment.characters.CharactersRoomDatabase
import com.example.finalfinalspace.datamanagment.characters.CharactersViewModel
import com.example.finalfinalspace.datamanagment.characters.CharactersViewModelFactory
import com.example.finalfinalspace.datamanagment.episodes.EpisodesInfo
import com.example.finalfinalspace.datamanagment.episodes.EpisodesRoomDatabase
import com.example.finalfinalspace.datamanagment.episodes.EpisodesViewModel
import com.example.finalfinalspace.datamanagment.episodes.EpisodesViewModelFactory


class EpisodeFragment  : Fragment() {

    private lateinit var ctx: Context
    private lateinit var episodeData: EpisodesInfo


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
        val database = EpisodesRoomDatabase.getDatabase(ctx)
        val episodesDao = database.episodeDao()
        episodeData = EpisodesViewModelFactory(episodesDao).create(EpisodesViewModel::class.java).retrieveEpisode(episodeId)
        view.findViewById<TextView>(R.id.episodeName).text = episodeData.name
        view.findViewById<TextView>(R.id.episodeDate).text =
            String.format(resources.getString(R.string.episodeAirDate), episodeData.airDate)
        view.findViewById<TextView>(R.id.episodeDirector).text =
            String.format(resources.getString(R.string.episodeDirector), episodeData.director)
        view.findViewById<TextView>(R.id.episodeWriter).text =
            String.format(resources.getString(R.string.episodeWriter), episodeData.writer)

        // characters
        var characterString: String = ""
        val charactersInEpiDatabase = CharInEpiRoomDatabase.getDatabase(ctx)
        val charInEpiDao = charactersInEpiDatabase.charInEpiDao()
        val charactersDatabase = CharactersRoomDatabase.getDatabase(ctx)
        val charactersDao = charactersDatabase.charactersDao()

        val characters = CharInEpiViewModelFactory(charInEpiDao).create(CharInEpiViewModel::class.java)
            .getCharactersInEpisode(episodeId)
        for (character: CharInEpiInfo in characters) {
            val charName = CharactersViewModelFactory(charactersDao).create(CharactersViewModel::class.java)
                .retrieveCharacter(character.character_id).name
            if (characterString.length == 0) {
                characterString = charName
            } else {
                characterString = characterString + ", " + charName
            }
        }
        view.findViewById<TextView>(R.id.episodeCharacters).text = characterString

        val epIdStr = episodeId.toString()
        var path = ctx.getExternalFilesDir(null).toString() + "/images/image$epIdStr.jpg"
        val bitmap = BitmapFactory.decodeFile(path)
        view.findViewById<ImageView>(R.id.episodeImage).setImageBitmap(bitmap)

        return view
    }


}