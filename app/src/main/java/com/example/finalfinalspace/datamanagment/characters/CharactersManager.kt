package com.example.finalfinalspace.datamanagment.characters

import com.example.finalfinalspace.datamanagment.FinalSpaceAPI
import javax.inject.Inject

class CharactersManager @Inject constructor(
    private val charactersDAO: CharactersDAO,
    private val finalSpaceAPI: FinalSpaceAPI
) {

    suspend fun downloadCharacters() {
        charactersDAO.insertAll(finalSpaceAPI.getCharacters())
    }
}