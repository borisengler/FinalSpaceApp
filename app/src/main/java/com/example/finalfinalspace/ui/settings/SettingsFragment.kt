package com.example.finalfinalspace.ui.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat.recreate
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.finalfinalspace.R
import com.example.finalfinalspace.data.prefs.enums.Themes
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat() {

    private val settingsVM: SettingsViewModel by viewModels()
    private var autoSync: Preference? = null
    private var appTheme: Preference? = null
    private var version: Preference? = null
    private var visitApi: Preference? = null
    private var downloadData: Preference? = null
    private var welcomeMessage: Preference? = null

    companion object {
        const val API_URL = "https://finalspaceapi.com/"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        autoSync = findPreference("autoSync")
        autoSync?.setDefaultValue(settingsVM.autoSync)

        appTheme = findPreference("appTheme")
        val appThemeValue = Themes.toString(settingsVM.appTheme)
        appTheme?.setDefaultValue(appThemeValue)
        appTheme?.setOnPreferenceChangeListener { _, newValue ->
            if (appThemeValue != newValue) {
                activity?.let { recreate(it) }
            }
            true
        }

        version = findPreference("version")
        version?.summary = settingsVM.getVersion()

        visitApi = findPreference("visitApi")
        visitApi?.intent = Intent(Intent.ACTION_VIEW, Uri.parse(API_URL))

        downloadData = findPreference("downloadData")
        downloadData?.setOnPreferenceClickListener {
            settingsVM.downloadData()
            true
        }

        welcomeMessage = findPreference("welcomeMessage")
        welcomeMessage?.summary = settingsVM.welcomeMessage
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    settingsVM.downloadStatus.collectLatest {
                        if (it) {
                            Toast.makeText(
                                context,
                                getString(R.string.dataDownloaded),
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                context,
                                getString(R.string.unableToSync),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
        return view
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }
}
