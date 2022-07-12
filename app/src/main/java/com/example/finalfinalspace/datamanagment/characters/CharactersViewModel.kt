package com.example.finalfinalspace.datamanagment.characters

import androidx.lifecycle.ViewModel
import com.example.finalfinalspace.datamanagment.network.CharactersManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(private val charactersManager: CharactersManager) : ViewModel() {

    /**
     * Retrieve an item from the repository.
     */
    suspend fun retrieveCharacters(ids: List<Int>): List<CharactersInfo> {
        return charactersManager.fetchCharacters(ids)
    }

    val characters = charactersManager.characters

}

