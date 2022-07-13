package com.example.finalfinalspace.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalfinalspace.R
import com.example.finalfinalspace.datamanagment.quotes.QuotesViewModel
import com.example.finalfinalspace.fragments.adapters.QuotesRWAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
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
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                quotesVM.quotes.collectLatest {
                        quotesRWAdapter.submitList(it)
                }
            }
        }

        // set the recyclerview
        val view: View = inflater.inflate(R.layout.fragment_quotes, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.quotes)
        recyclerView.layoutManager = LinearLayoutManager(container!!.context)
        recyclerView.adapter = quotesRWAdapter

        return view
    }

}