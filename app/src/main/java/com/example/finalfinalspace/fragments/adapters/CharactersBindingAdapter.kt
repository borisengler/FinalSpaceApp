package com.example.finalfinalspace.fragments.adapters

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.finalfinalspace.datamanagment.characters.CharactersInfo
import com.example.finalfinalspace.datamanagment.episodes.EpisodeWithCharactersInfo

object CharactersBindingAdapter {

    @BindingAdapter("list", "listSeparator", requireAll = false)
    fun TextView.updateCharacters(list: List<String>, listSeparator: String?) {

        text = list.joinToString(listSeparator ?: ", ")
    }
}