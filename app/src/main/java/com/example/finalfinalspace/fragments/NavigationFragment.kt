package com.example.finalfinalspace.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.finalfinalspace.R
import com.example.finalfinalspace.datamanagment.FinalSpaceAPIObject
import com.example.finalfinalspace.datamanagment.quotes.QuotesDAO
import com.example.finalfinalspace.workers.SyncDataWorker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NavigationFragment : Fragment(), View.OnClickListener {


    private lateinit var navController: NavController
    private lateinit var ctx: Context
    @Inject lateinit var quotesDao: QuotesDAO
    private val scope = MainScope()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
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
        when(v!!.id) {
            R.id.syncButton -> syncData()
            R.id.quotesButton -> navController.navigate(R.id.action_navigationFragment_to_quotesFragment)
            R.id.episodesButton -> navController.navigate(R.id.action_navigationFragment_to_episodesFragment)
        }
    }

    private fun syncData() {
        val workManager = WorkManager.getInstance(ctx)
        workManager.enqueue(OneTimeWorkRequest.from(SyncDataWorker::class.java))
        scope.launch {
            val quotes = FinalSpaceAPIObject.retrofitService.getQuotes()
            quotesDao.insertAll(quotes)
        }
    }

}