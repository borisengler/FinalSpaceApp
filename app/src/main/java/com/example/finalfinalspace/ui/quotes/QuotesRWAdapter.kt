package com.example.finalfinalspace.ui.quotes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.finalfinalspace.data.db.models.QuotesInfo
import com.example.finalfinalspace.databinding.ItemQuoteBinding
import javax.inject.Inject

class QuotesRWAdapter @Inject constructor() :
    ListAdapter<QuotesInfo, QuotesRWAdapter.QuoteViewHolder>(QuotesDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): QuoteViewHolder {
        return QuoteViewHolder(ItemQuoteBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class QuoteViewHolder(private val binding: ItemQuoteBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(info: QuotesInfo) {
            binding.quoteAuthor.text = info.by
            binding.quoteText.text = info.quote
        }
    }

    class QuotesDiffCallback : DiffUtil.ItemCallback<QuotesInfo>() {
        override fun areItemsTheSame(oldItem: QuotesInfo, newItem: QuotesInfo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: QuotesInfo, newItem: QuotesInfo): Boolean {
            return oldItem == newItem
        }
    }
}
