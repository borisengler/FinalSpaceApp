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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalfinalspace.R
import com.example.finalfinalspace.databinding.FragmentQuotesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class QuotesFragment : Fragment() {
    @Inject lateinit var quotesRWAdapter: QuotesRWAdapter

    private val quotesVM: QuotesViewModel by viewModels()
    private var binding: FragmentQuotesBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentQuotesBinding.inflate(inflater, container, false)

        // set the recyclerview
        with(binding?.quotes) {
            (this?.layoutManager as GridLayoutManager).spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int =
                    when (quotesRWAdapter.getItemViewType(position)) {
                        QuotesRWAdapter.TYPE_CHARACTER -> resources.getInteger(R.integer.spanCount)
                        QuotesRWAdapter.TYPE_QUOTE -> resources.getInteger(R.integer.spanCountHalf)
                        else -> resources.getInteger(R.integer.spanCount)
                    }
            }
            this.adapter = quotesRWAdapter
            this.adapter?.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        }

        // set refresh swipe
        binding?.quotesRefresh?.setOnRefreshListener {
            quotesVM.downloadData()
        }

        binding?.searchQuotes?.addTextChangedListener(QuotesSearchListener(quotesVM))

        // Get quotes data
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    quotesVM.quotes.collectLatest { data ->
                        if (data.isNotEmpty()) {
                            binding?.quotes?.visibility = View.VISIBLE
                            binding?.empty?.visibility = View.GONE
                            quotesRWAdapter.submitList(data)
                            binding?.quotes?.smoothScrollToPosition(0)
                        } else {
                            binding?.quotes?.visibility = View.GONE
                            binding?.empty?.visibility = View.VISIBLE
                        }
                    }
                }
                launch {
                    quotesVM.errorMessage.collectLatest {
                        Toast.makeText(
                            context,
                            getString(R.string.unableToSync),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                launch {
                    quotesVM.downloading.collectLatest {
                        binding?.quotesRefresh?.isRefreshing = it
                    }
                }
            }
        }
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
