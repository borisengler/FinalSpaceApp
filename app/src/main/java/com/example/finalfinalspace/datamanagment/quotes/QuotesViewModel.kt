package com.example.finalfinalspace.datamanagment.quotes

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QuotesViewModel @Inject constructor(private val quotesDao: QuotesDAO) : ViewModel() {

    /**
     * Retrieve an item from the repository.
     */
    fun retrieveAllQuotes(): List<QuotesInfo> {
        return quotesDao.getAllQuotes()
    }

}

///**
// * Factory class to instantiate the [ViewModel] instance.
// */
//@Singleton
//class QuotesViewModelFactory @Inject constructor(private val quotesDao: QuotesDAO) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(QuotesViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return QuotesViewModel(quotesDao) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}
