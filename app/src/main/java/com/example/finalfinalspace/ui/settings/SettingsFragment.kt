package com.example.finalfinalspace.ui.settings

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
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
import com.example.finalfinalspace.R
import com.example.finalfinalspace.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentSettingsBinding
    private val settingsVM: SettingsViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)

        binding.autoSync.isChecked = settingsVM.autoSync
        binding.version.text = getString(R.string.version, settingsVM.getVersion())

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    settingsVM.downloadStatus.collectLatest {
                        if (it) {
                            Toast.makeText(
                                context,
                                "Data downloaded",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                context,
                                "Unable to sync data",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.syncAll.setOnClickListener(this)
        binding.autoSync.setOnClickListener(this)
        binding.API.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.syncAll -> syncData()
            binding.autoSync -> autoSync()
            binding.API -> openApiDialog()
        }
    }

    private fun syncData() {
        settingsVM.downloadData()
    }

    private fun autoSync() {
        settingsVM.setAutoSync(binding.autoSync.isChecked)
    }

    private fun openApiDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setMessage("This will take you outside the app. Proceed?")
            .setCancelable(false)
            .setPositiveButton("OK") { _, _ ->
                goToApi()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }
        val alert = builder.create()
        alert.show()
    }

    private fun goToApi() {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(SettingsViewModel.API_URL))
        startActivity(browserIntent)
    }
}
