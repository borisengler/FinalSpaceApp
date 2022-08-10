package com.example.finalfinalspace.data.prefs

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

class SettingsStorage @Inject constructor(private val sharedPref: SharedPreferences) {

    companion object {
        internal const val AUTO_SYNC = "autoSync"
    }

    fun getAutoSync(): Boolean {
        return sharedPref.getBoolean(AUTO_SYNC, true)
    }

    fun setAutoSync(value: Boolean) {
        sharedPref.edit { putBoolean(AUTO_SYNC, value) }
    }
}
