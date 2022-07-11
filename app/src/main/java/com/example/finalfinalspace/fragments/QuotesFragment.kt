package com.example.finalfinalspace.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalfinalspace.R
import com.example.finalfinalspace.datamanagment.quotes.QuotesInfo
import com.example.finalfinalspace.datamanagment.quotes.QuotesViewModel
import com.example.finalfinalspace.fragments.adapters.QuotesRWAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuotesFragment : Fragment() {

    private lateinit var quotesData: List<QuotesInfo>

    private val quotesVM: QuotesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Get quotes data
        quotesData = quotesVM.retrieveAllQuotes()

        // set the recyclerview
        val view: View = inflater.inflate(R.layout.fragment_quotes, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.quotes)
        val emptyView: TextView = view.findViewById(R.id.empty_quotes)
        recyclerView.layoutManager = LinearLayoutManager(container!!.context)
        recyclerView.adapter = QuotesRWAdapter(container.context, quotesData)

        // set visibility of views
        if (quotesData.isEmpty()) {
            recyclerView.visibility = View.GONE
            emptyView.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            emptyView.visibility = View.GONE
        }
        return view
    }

}