package com.example.finalfinalspace.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalfinalspace.R
import com.example.finalfinalspace.datamanagment.quotes.QuotesInfo
import com.example.finalfinalspace.datamanagment.quotes.QuotesRoomDatabase
import com.example.finalfinalspace.datamanagment.quotes.QuotesViewModel
import com.example.finalfinalspace.datamanagment.quotes.QuotesViewModelFactory
import com.example.finalfinalspace.fragments.adapters.QuotesRWAdapter


class QuotesFragment  : Fragment() {

    lateinit var ctx: Context
    lateinit var quotesData: List<QuotesInfo>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (container != null) {
            ctx = container.getContext()
        };
        // Get quotes data
        val database = QuotesRoomDatabase.getDatabase(ctx)
        val quotesDao = database.quotesDao()
        quotesData = QuotesViewModelFactory(quotesDao).create(QuotesViewModel::class.java).retrieveAllQuotes()

        // set the recyclerview
        val view: View = inflater.inflate(R.layout.fragment_quotes, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.quotes)
        val emptyView: TextView = view.findViewById(R.id.empty_quotes)
        recyclerView.layoutManager = LinearLayoutManager(ctx)
        recyclerView.adapter = QuotesRWAdapter(ctx, quotesData)

        // set visibility of views
        if (quotesData.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
        else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
        return view
    }


}