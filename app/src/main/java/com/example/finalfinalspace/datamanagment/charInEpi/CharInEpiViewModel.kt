package com.example.finalfinalspace.datamanagment.charInEpi

import androidx.lifecycle.*


class CharInEpiViewModel(private val charInEpisDao: CharInEpiDAO) : ViewModel()  {

    lateinit var allItems: List<CharInEpiInfo>

    /**
     * Retrieve an item from the repository.
     */
    fun getCharactersInEpisode(episode_id: Int): List<CharInEpiInfo> {
        allItems = charInEpisDao.getCharactersInEpisode(episode_id)
        return allItems
    }

}


/**
 * Factory class to instantiate the [ViewModel] instance.
 */
class CharInEpiViewModelFactory(private val charInEpiDao: CharInEpiDAO) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharInEpiViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CharInEpiViewModel(charInEpiDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
