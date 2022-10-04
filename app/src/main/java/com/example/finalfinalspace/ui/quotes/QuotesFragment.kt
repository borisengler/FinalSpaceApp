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
import androidx.paging.LoadState
import androidx.recyclerview.widget.ConcatAdapter
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
    @Inject lateinit var quotesPagingAdapter: QuotesPagingAdapter
    @Inject lateinit var emptyHeaderAdapter: EmptyHeaderAdapter

    private val quotesVM: QuotesViewModel by viewModels()
    private var binding: FragmentQuotesBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentQuotesBinding.inflate(inflater, container, false)

        val concatAdapter = ConcatAdapter(
            ConcatAdapter.Config.Builder().setIsolateViewTypes(false).build(),
            emptyHeaderAdapter,
            quotesPagingAdapter
        )

        // set the recyclerview
        with(binding?.quotes) {
            (this?.layoutManager as GridLayoutManager).spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (concatAdapter.getItemViewType(position)) {
                        QuotesPagingAdapter.TYPE_QUOTE -> resources.getInteger(R.integer.spanCountHalf)
                        else -> resources.getInteger(R.integer.spanCount)
                    }
                }
            }
            quotesPagingAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
            this.adapter = concatAdapter
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
                    quotesPagingAdapter.addLoadStateListener { loadState ->

                        if (
                            loadState.source.refresh is LoadState.NotLoading &&
                            loadState.append.endOfPaginationReached &&
                            quotesPagingAdapter.itemCount < 1
                        ) {
                            binding?.quotes?.visibility = View.GONE
                            binding?.empty?.visibility = View.VISIBLE
                        } else {
                            binding?.quotes?.visibility = View.VISIBLE
                            binding?.empty?.visibility = View.GONE
                        }
                    }
                }
                launch {
                    quotesVM.quotes.collectLatest { data ->
                        quotesPagingAdapter.submitData(data)
                        binding?.quotes?.smoothScrollToPosition(0)
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
