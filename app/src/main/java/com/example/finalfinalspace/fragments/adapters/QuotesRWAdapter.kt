package com.example.finalfinalspace.fragments.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.finalfinalspace.R
import com.example.finalfinalspace.datamanagment.quotes.QuotesInfo
import javax.inject.Inject

class QuotesRWAdapter @Inject constructor() :
    ListAdapter<QuotesInfo, QuotesRWAdapter.QuoteViewHolder>(QuotesDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): QuotesRWAdapter.QuoteViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.quote_cardview, parent, false)
        return QuoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuotesRWAdapter.QuoteViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class QuoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var quoteTextView: TextView = itemView.findViewById(R.id.quote_text)
        private var quoteAuthorView: TextView = itemView.findViewById(R.id.quote_author)

        fun bind(info: QuotesInfo) {
            quoteTextView.text = info.quote
            quoteAuthorView.text = info.by
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
