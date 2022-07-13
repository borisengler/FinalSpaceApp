package com.example.finalfinalspace.datamanagment.characters

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(private val charactersManager: CharactersManager) : ViewModel() {

    /**
     * Retrieve an item from the repository.
     */
    fun retrieveCharacters(ids: List<Int>): Flow<List<CharactersInfo>> {
        return charactersManager.fetchCharacters(ids)
    }

    val characters = charactersManager.characters

}

