package com.example.finalfinalspace.data.prefs

import android.content.SharedPreferences
import androidx.core.app.ActivityCompat.recreate
import androidx.core.content.edit
import com.example.finalfinalspace.data.prefs.enums.Themes
import javax.inject.Inject

class SettingsStorage @Inject constructor(private val sharedPref: SharedPreferences) {

    companion object {
        internal const val AUTO_SYNC = "autoSync"
        internal const val APP_THEME = "appTheme"
    }

    fun getAutoSync(): Boolean {
        return sharedPref.getBoolean(AUTO_SYNC, true)
    }

    fun setAutoSync(value: Boolean) {
        sharedPref.edit { putBoolean(AUTO_SYNC, value) }
    }

    fun getAppTheme(): Themes {
        return sharedPref.getString(APP_THEME, "1")?.let { Themes.fromString(it) }!!
    }

    fun setAppTheme(theme: Themes) {
        sharedPref.edit { putString(APP_THEME, Themes.toString(theme)) }
    }
}
