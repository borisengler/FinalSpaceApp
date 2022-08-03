package com.example.finalfinalspace.datamanagment.characters

import android.widget.Toast
import com.example.finalfinalspace.datamanagment.FinalSpaceAPI
import java.net.UnknownHostException
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
