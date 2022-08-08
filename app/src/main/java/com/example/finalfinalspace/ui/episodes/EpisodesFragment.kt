package com.example.finalfinalspace.ui.episodes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.finalfinalspace.R
import com.example.finalfinalspace.databinding.FragmentEpisodesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class EpisodesFragment : Fragment() {
    @Inject lateinit var episodesRWAdapter: EpisodesRWAdapter

    private lateinit var navController: NavController
    private val episodesVM: EpisodesViewModel by viewModels()
    private var binding: FragmentEpisodesBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEpisodesBinding.inflate(inflater, container, false)

        // set the recyclerview
        with(binding?.episodesRecyclerView) {
            this?.adapter = episodesRWAdapter
            this?.adapter?.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        }

        // set refresh swipe
        val swipeRefreshLayout = binding?.episodesRefresh
        swipeRefreshLayout?.setOnRefreshListener {
            episodesVM.downloadData()
        }

        // Get episodes data
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    episodesVM.episodes.collectLatest {
                        if (it.isNotEmpty()) {
                            binding?.episodesRecyclerView?.visibility = View.VISIBLE
                            binding?.empty?.visibility = View.GONE
                            episodesRWAdapter.submitList(it)
                        } else {
                            binding?.episodesRecyclerView?.visibility = View.GONE
                            binding?.empty?.visibility = View.VISIBLE
                        }
                    }
                }
                launch {
                    episodesVM.errorMessage.collectLatest {
                        Toast.makeText(
                            context,
                            getString(R.string.unableToSync),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                launch {
                    episodesVM.downloading.collectLatest {
                        binding?.episodesRefresh?.isRefreshing = it
                    }
                }
            }
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        episodesRWAdapter.setOnClickListener(object : EpisodesRWAdapter.OnItemClickListener {
            override fun onItemClick(id: Int) {
                val bundle: Bundle = bundleOf("episodeId" to id)
                navController.navigate(R.id.action_episodesFragment_to_episodeFragment, bundle)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
