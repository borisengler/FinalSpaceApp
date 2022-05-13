package com.example.finalfinalspace.datamanagment.characters

import androidx.lifecycle.*

class CharactersViewModel(private val charactersDao: CharactersDAO) : ViewModel()  {

    lateinit var allItems: List<CharactersInfo>

    /**
     * Retrieve an item from the repository.
     */
    fun retrieveCharacter(id: Int): CharactersInfo {
        return charactersDao.getCharacter(id)
    }

    /**
     * Retrieve an item from the repository.
     */
    fun retrieveAllCharacters(): List<CharactersInfo> {
        allItems = charactersDao.getAllCharacters()
        return charactersDao.getAllCharacters()
    }

}


/**
 * Factory class to instantiate the [ViewModel] instance.
 */
class CharactersViewModelFactory(private val charactersDao: CharactersDAO) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharactersViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CharactersViewModel(charactersDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
