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
import com.example.finalfinalspace.workers.SyncDataWorker


class NavigationFragment : Fragment(), View.OnClickListener {


    lateinit var navController: NavController
    lateinit var ctx: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        if (container != null) {
            ctx = container.getContext()
        };
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
            R.id.episodesButton -> navController!!.navigate(R.id.action_navigationFragment_to_episodesFragment)
//            R.id.quotes_button -> navController!!.navigate(R.id.action_navigationFragment_to_quotesFragment)
        }
    }

    fun syncData() {
        val workManager = WorkManager.getInstance(ctx)
        workManager.enqueue(OneTimeWorkRequest.from(SyncDataWorker::class.java))
    }

}