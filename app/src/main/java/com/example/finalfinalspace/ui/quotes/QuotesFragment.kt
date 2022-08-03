package com.example.finalfinalspace.ui.quotes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalfinalspace.databinding.FragmentQuotesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class QuotesFragment : Fragment() {

    @Inject lateinit var quotesRWAdapter: QuotesRWAdapter

    private val quotesVM: QuotesViewModel by viewModels()
    private lateinit var binding: FragmentQuotesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentQuotesBinding.inflate(inflater, container, false)

        // Get quotes data
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    quotesVM.quotes.collectLatest {
                        if (it.isNotEmpty()) {
                            binding.quotes.visibility = View.VISIBLE
                            binding.empty.visibility = View.GONE
                            quotesRWAdapter.submitList(it)
                        } else {
                            binding.quotes.visibility = View.GONE
                            binding.empty.visibility = View.VISIBLE
                        }
                    }
                }
                launch {
                    quotesVM.errorMessage.collectLatest {
                        Toast.makeText(
                            context,
                            "Unable to sync data",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        // set the recyclerview
        val recyclerView: RecyclerView = binding.quotes
        recyclerView.layoutManager = LinearLayoutManager(container!!.context)
        recyclerView.adapter = quotesRWAdapter

        // set refresh swipe
        val swipeRefreshLayout = binding.quotesRefresh
        swipeRefreshLayout.setOnRefreshListener {
            quotesVM.downloadData()
            swipeRefreshLayout.isRefreshing = false
        }

        return binding.root
    }
}
