package com.example.finalfinalspace.ui.quotes

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class QuotesSearchListener @Inject constructor(private val quotesViewModel: QuotesViewModel): TextWatcher {
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        quotesViewModel.viewModelScope.launch(quotesViewModel.mainDispatcher) {
            quotesViewModel.filter.emit(p0.toString())
        }
    }

    override fun afterTextChanged(p0: Editable?) {
    }

}