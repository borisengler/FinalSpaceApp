package com.example.finalfinalspace.datamanagment.episodes

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EpisodesViewModel @Inject constructor(private val episodesDao: EpisodesDAO) : ViewModel() {

    lateinit var allItems: List<EpisodesInfo>

    /**
     * Retrieve an item from the repository.
     */
    fun retrieveEpisode(id: Int): EpisodesInfo {
        return episodesDao.getEpisode(id)
    }

    /**
     * Retrieve an item from the repository.
     */
    fun retrieveAllEpisodes(): List<EpisodesInfo> {
        allItems = episodesDao.getAllEpisodes()
        return episodesDao.getAllEpisodes()
    }

}

///**
// * Factory class to instantiate the [ViewModel] instance.
// */
//class EpisodesViewModelFactory(private val episodesDao: EpisodesDAO) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(EpisodesViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return EpisodesViewModel(episodesDao) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}
