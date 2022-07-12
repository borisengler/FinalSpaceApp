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
import com.example.finalfinalspace.datamanagment.quotes.QuotesViewModel
import com.example.finalfinalspace.fragments.adapters.QuotesRWAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class QuotesFragment : Fragment() {

    @Inject lateinit var quotesRWAdapter: QuotesRWAdapter

    private val quotesVM: QuotesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Get quotes data
        quotesVM.quotes.observe(viewLifecycleOwner) {
            quotesRWAdapter.submitList(it)
        }

        // set the recyclerview
        val view: View = inflater.inflate(R.layout.fragment_quotes, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.quotes)
        recyclerView.layoutManager = LinearLayoutManager(container!!.context)
        recyclerView.adapter = quotesRWAdapter

        return view
    }

}