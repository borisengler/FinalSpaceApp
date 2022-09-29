package com.example.finalfinalspace.ui.quotes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalfinalspace.data.db.models.CharactersInfo
import com.example.finalfinalspace.data.db.models.QuoteOrCharacter
import com.example.finalfinalspace.data.db.models.QuotesInfo
import com.example.finalfinalspace.databinding.ItemCharacterBinding
import com.example.finalfinalspace.databinding.ItemQuoteBinding
import javax.inject.Inject

class QuotesRWAdapter @Inject constructor() :
    ListAdapter<QuoteOrCharacter, RecyclerView.ViewHolder>(QuoteOrCharacterDiffCallback()) {

    companion object {
        internal const val TYPE_CHARACTER = 0
        internal const val TYPE_QUOTE = 1
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_CHARACTER ->
                CharacterViewHolder(ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> {
                QuoteViewHolder(ItemQuoteBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is QuoteViewHolder -> holder.bind(getItem(position) as QuotesInfo)
            is CharacterViewHolder -> holder.bind(getItem(position) as CharactersInfo)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is CharactersInfo -> TYPE_CHARACTER
            else -> {
                TYPE_QUOTE
            }
        }
    }

    inner class QuoteViewHolder(private val binding: ItemQuoteBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(info: QuotesInfo) {
            binding.quoteText.text = info.quote
        }
    }

    inner class CharacterViewHolder(private val binding: ItemCharacterBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(info: CharactersInfo) {
            binding.quoteAuthor.text = info.name
            Glide.with(binding.root)
                .load(info.imageUrl)
                .circleCrop()
                .into(binding.characterImg)
        }
    }

    class QuoteOrCharacterDiffCallback : DiffUtil.ItemCallback<QuoteOrCharacter>() {
        override fun areItemsTheSame(oldItem: QuoteOrCharacter, newItem: QuoteOrCharacter): Boolean {
            return oldItem.uid == newItem.uid
        }

        override fun areContentsTheSame(oldItem: QuoteOrCharacter, newItem: QuoteOrCharacter): Boolean {
            return oldItem == newItem
        }
    }
}
