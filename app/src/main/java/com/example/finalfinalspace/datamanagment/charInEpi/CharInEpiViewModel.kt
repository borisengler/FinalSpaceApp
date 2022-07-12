package com.example.finalfinalspace.datamanagment.charInEpi

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import com.example.finalfinalspace.datamanagment.network.CharInEpiManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharInEpiViewModel @Inject constructor(private val charInEpiManager: CharInEpiManager) : ViewModel() {

    val charsInEpi = charInEpiManager.charsInEpi

    suspend fun getCharactersInEpisode(episodeId: Int): List<CharInEpiInfo> {
        return charInEpiManager.fetchCharactersInEpisode(episodeId)
    }

}

