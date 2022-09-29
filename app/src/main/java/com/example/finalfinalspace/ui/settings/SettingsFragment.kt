package com.example.finalfinalspace.ui.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat.recreate
import androidx.core.text.bold
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreference
import com.example.finalfinalspace.R
import com.example.finalfinalspace.data.prefs.SettingsStorage
import com.example.finalfinalspace.data.prefs.enums.Themes
import com.example.finalfinalspace.databinding.FragmentSettingsBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment: PreferenceFragmentCompat() {

    private val settingsVM: SettingsViewModel by viewModels()
    private var autoSync: Preference? = null
    private var appTheme: Preference? = null
    private var version: Preference? = null
    private var visitApi: Preference? = null
    private var downloadData: Preference? = null

    companion object {

        const val API_URL = "https://finalspaceapi.com/"
    }

//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): FrameLayout? {
//        binding = FragmentSettingsBinding.inflate(inflater, container, false)
//
//        binding?.autoSync?.isChecked = settingsVM.autoSync
//        binding?.author?.text = SpannableStringBuilder()
//            .append(getString(R.string.author) + "\n")
//            .bold { append(getString(R.string.version, settingsVM.getVersion())) }
//

//        binding?.syncAll?.setOnClickListener { syncData() }
//        binding?.autoSync?.setOnClickListener { autoSync() }
//        binding?.API?.setOnClickListener { openApiDialog() }
//
//        return binding?.root
//    }
//
//    private fun syncData() {
//        settingsVM.downloadData()
//    }
//
//    private fun autoSync() {
//        settingsVM.setAutoSync(binding?.autoSync?.isChecked ?: true)
//    }
//
//    private fun openApiDialog() {
//        val builder = context?.let { MaterialAlertDialogBuilder(it) }
//        builder?.let {
//            it.setTitle(R.string.dialogTitle)
//                .setMessage(getString(R.string.dialogLeaveApp))
//                .setCancelable(false)
//                .setPositiveButton(getString(R.string.ok)) { _, _ ->
//                    goToApi()
//                }.setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
//                    dialog.cancel()
//                }.setIcon(R.drawable.ic_out_of_app)
//            val alert = builder.create()
//            alert.show()
//        }
//    }
//
//    private fun goToApi() {
//        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(API_URL))
//        startActivity(browserIntent)
//    }

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
