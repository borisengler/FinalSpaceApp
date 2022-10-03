package com.example.finalfinalspace.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.finalfinalspace.R
import com.example.finalfinalspace.data.prefs.SettingsStorage
import com.example.finalfinalspace.data.prefs.enums.Themes
import com.example.finalfinalspace.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private lateinit var navController: NavController
    private val mainVM: MainViewModel by viewModels()
    @Inject lateinit var settingsStorage: SettingsStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.frame) as NavHostFragment
        navController = navHostFragment.navController

        binding?.toolbar?.setupWithNavController(
            navController,
            AppBarConfiguration(setOf(R.id.episodesFragment, R.id.quotesFragment, R.id.settingsFragment))
        )

        when (settingsStorage.getAppTheme()) {
            Themes.SYSTEM_DEFAULT -> AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM)
            Themes.LIGHT -> AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
            Themes.DARK -> AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
        }

        binding?.navigationbar?.setupWithNavController(navController)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    mainVM.downloadStatus.collectLatest {
                        Timber.d(it.toString())
                        if (it) {
                            Toast.makeText(
                                this@MainActivity,
                                getString(R.string.dataDownloaded),
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                this@MainActivity,
                                getString(R.string.unableToSync),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }
}
