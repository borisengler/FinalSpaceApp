package com.example.finalfinalspace.datamanagment.charInEpi

import androidx.lifecycle.ViewModel
import com.example.finalfinalspace.datamanagment.characters.CharactersInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharInEpiViewModel @Inject constructor(private val charInEpiManager: CharInEpiManager) : ViewModel() {

    val charsInEpi = charInEpiManager.charsInEpi

    suspend fun getCharactersInEpisode(episodeId: Int): List<CharactersInfo> {
        return charInEpiManager.fetchCharactersInEpisode(episodeId)
    }

}

