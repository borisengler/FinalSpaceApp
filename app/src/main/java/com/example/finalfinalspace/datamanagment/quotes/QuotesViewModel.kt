package com.example.finalfinalspace.datamanagment.quotes

import androidx.lifecycle.*

class QuotesViewModel(private val quotesDao: QuotesDAO) : ViewModel()  {

    lateinit var allItems: List<QuotesInfo>

    /**
     * Retrieve an item from the repository.
     */
    fun retrieveAllQuotes(): List<QuotesInfo> {
        allItems = quotesDao.getAllQuotes()
        return allItems
    }

}


/**
 * Factory class to instantiate the [ViewModel] instance.
 */
class QuotesViewModelFactory(private val quotesDao: QuotesDAO) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuotesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return QuotesViewModel(quotesDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
