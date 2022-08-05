package com.example.finalfinalspace.domain

import com.example.finalfinalspace.data.api.FinalSpaceAPI
import com.example.finalfinalspace.data.db.CharactersDAO
import javax.inject.Inject

class CharactersManager @Inject constructor(
    private val charactersDAO: CharactersDAO,
    private val finalSpaceAPI: FinalSpaceAPI
) {

    suspend fun downloadCharacters() {
        val characters = finalSpaceAPI.getCharacters()
        charactersDAO.insertAll(characters)
    }
}
