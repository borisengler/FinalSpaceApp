package com.example.finalfinalspace.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.finalfinalspace.R
import com.example.finalfinalspace.datamanagment.characters.CharactersManager
import com.example.finalfinalspace.datamanagment.episodes.EpisodesManager
import com.example.finalfinalspace.datamanagment.network.FinalSpaceDownloadManager
import com.example.finalfinalspace.datamanagment.quotes.QuotesManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NavigationFragment : Fragment(), View.OnClickListener {

    private lateinit var navController: NavController
    private lateinit var ctx: Context

    //    TODO vsetky dao potrebujem len kvoli download buttonu, prerobit (asi na service)
    @Inject lateinit var quotesManager: QuotesManager
    @Inject lateinit var charactersManager: CharactersManager
    @Inject lateinit var episodesManager: EpisodesManager
    private val scope = MainScope()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        if (container != null) {
            ctx = container.context
        }
        return inflater.inflate(R.layout.fragment_navigation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        view.findViewById<Button>(R.id.syncButton).setOnClickListener(this)
        view.findViewById<Button>(R.id.quotesButton).setOnClickListener(this)
        view.findViewById<Button>(R.id.episodesButton).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.syncButton -> syncData()
            R.id.quotesButton -> navController.navigate(R.id.action_navigationFragment_to_quotesFragment)
            R.id.episodesButton -> navController.navigate(R.id.action_navigationFragment_to_episodesFragment)
        }
    }

    private fun syncData() {
        scope.launch {
            FinalSpaceDownloadManager(quotesManager, charactersManager, episodesManager).downloadAllData()
        }
    }
}
