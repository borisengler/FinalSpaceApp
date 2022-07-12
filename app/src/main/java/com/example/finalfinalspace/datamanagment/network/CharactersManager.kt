package com.example.finalfinalspace.datamanagment.network

import androidx.lifecycle.distinctUntilChanged
import com.example.finalfinalspace.datamanagment.FinalSpaceAPI
import com.example.finalfinalspace.datamanagment.characters.CharactersDAO
import com.example.finalfinalspace.datamanagment.characters.CharactersInfo
import javax.inject.Inject

class CharactersManager @Inject constructor(
    private val charactersDAO: CharactersDAO,
    private val finalSpaceAPI: FinalSpaceAPI
) {

    suspend fun fetchCharacters(ids: List<Int>): List<CharactersInfo>{
        return charactersDAO.fetchCharacters(ids)
    }

    val characters = charactersDAO.getAllCharacters().distinctUntilChanged()

    suspend fun downloadCharacters() {
        charactersDAO.insertAll(finalSpaceAPI.getCharacters())
    }
}