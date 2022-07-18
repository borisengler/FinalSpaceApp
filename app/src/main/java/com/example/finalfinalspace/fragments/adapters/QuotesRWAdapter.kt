package com.example.finalfinalspace.fragments.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.finalfinalspace.R
import com.example.finalfinalspace.databinding.EpisodeCardviewBinding
import com.example.finalfinalspace.databinding.QuoteCardviewBinding
import com.example.finalfinalspace.datamanagment.quotes.QuotesInfo
import javax.inject.Inject

class QuotesRWAdapter @Inject constructor() :
    ListAdapter<QuotesInfo, QuotesRWAdapter.QuoteViewHolder>(QuotesDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): QuotesRWAdapter.QuoteViewHolder {
        return QuoteViewHolder(QuoteCardviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    }

    override fun onBindViewHolder(holder: QuotesRWAdapter.QuoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class QuoteViewHolder(private val binding: QuoteCardviewBinding) : RecyclerView.ViewHolder(binding.root) {

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
