package com.example.finalfinalspace.fragments.adapters

import android.widget.TextView
import androidx.databinding.BindingAdapter

object CharactersBindingAdapter {

    @BindingAdapter("list", "listSeparator", requireAll = false)
    fun TextView.updateCharacters(list: List<String>, listSeparator: String?) {

        text = list.joinToString(listSeparator ?: ", ")
    }
}
