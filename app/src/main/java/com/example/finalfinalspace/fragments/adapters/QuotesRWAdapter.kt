package com.example.finalfinalspace.fragments.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalfinalspace.R
import com.example.finalfinalspace.datamanagment.quotes.QuotesInfo

class QuotesRWAdapter(context: Context, quotesData: List<QuotesInfo>)
    : RecyclerView.Adapter<QuotesRWAdapter.QuoteViewHolder>() {

    private val ctx: Context = context
    private val quotes: List<QuotesInfo> = quotesData

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): QuotesRWAdapter.QuoteViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(ctx)
        val view: View = inflater.inflate(R.layout.quote_cardview, parent, false)
        return QuoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuotesRWAdapter.QuoteViewHolder, position: Int) {
        holder.quoteTextView.text = quotes[position].quote
        holder.quoteAuthorView.text = quotes[position].by
    }

    override fun getItemCount(): Int {
        return quotes.size
    }

    inner class QuoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var quoteTextView: TextView = itemView.findViewById(R.id.quote_text)
        var quoteAuthorView: TextView = itemView.findViewById(R.id.quote_author)
    }
}