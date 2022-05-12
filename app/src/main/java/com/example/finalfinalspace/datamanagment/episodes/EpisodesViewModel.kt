package com.example.finalfinalspace.datamanagment.episodes

import androidx.lifecycle.*

class EpisodesViewModel(private val episodesDao: EpisodesDAO) : ViewModel()  {

    val allItems: LiveData<List<EpisodesInfo>> = episodesDao.getAllEpisodes().asLiveData()

    /**
     * Retrieve an item from the repository.
     */
    fun retrieveEpisode(id: Int): LiveData<EpisodesInfo> {
        return episodesDao.getEpisode(id).asLiveData()
    }

    /**
     * Retrieve an item from the repository.
     */
    fun retrieveAllEpisodes(): LiveData<List<EpisodesInfo>> {
        return episodesDao.getAllEpisodes().asLiveData()
    }

}


/**
 * Factory class to instantiate the [ViewModel] instance.
 */
class EpisodesViewModelFactory(private val episodesDao: EpisodesDAO) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EpisodesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EpisodesViewModel(episodesDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
