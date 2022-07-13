package com.example.finalfinalspace.datamanagment.characters

import com.example.finalfinalspace.datamanagment.FinalSpaceAPI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class CharactersManager @Inject constructor(
    private val charactersDAO: CharactersDAO,
    private val finalSpaceAPI: FinalSpaceAPI
) {

    fun fetchCharacters(ids: List<Int>): Flow<List<CharactersInfo>> {
        return charactersDAO.getCharacters(ids)
    }

    val characters = charactersDAO.getAllCharacters().distinctUntilChanged()

    suspend fun downloadCharacters() {
        charactersDAO.insertAll(finalSpaceAPI.getCharacters())
    }
}