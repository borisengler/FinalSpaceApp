package com.example.finalfinalspace.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalfinalspace.BuildConfig
import com.example.finalfinalspace.data.prefs.SettingsStorage
import com.example.finalfinalspace.data.prefs.enums.Themes
import com.example.finalfinalspace.di.qualifiers.IoDispatcher
import com.example.finalfinalspace.di.qualifiers.WelcomeMessage
import com.example.finalfinalspace.domain.CharactersManager
import com.example.finalfinalspace.domain.EpisodesManager
import com.example.finalfinalspace.domain.QuotesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val episodesManager: EpisodesManager,
    private val charactersManager: CharactersManager,
    private val quotesManager: QuotesManager,
    private val settingsStorage: SettingsStorage,
    @IoDispatcher val ioDispatcher: CoroutineDispatcher,
    @WelcomeMessage val message: String
) : ViewModel() {

    private val _downloadStatus = MutableSharedFlow<Boolean>()
    val downloadStatus = _downloadStatus.asSharedFlow()

    val autoSync get() = settingsStorage.getAutoSync()
    val appTheme get() = settingsStorage.getAppTheme()
    val welcomeMessage get() = message

    fun downloadData() {
        viewModelScope.launch(ioDispatcher) {
            runCatching {
                charactersManager.downloadCharacters()
                episodesManager.downloadEpisodes()
                quotesManager.downloadQuotes()
            }.onFailure {
                Timber.e(it)
                _downloadStatus.emit(false)
            }.onSuccess {
                _downloadStatus.emit(true)
            }
        }
    }

    fun setAutoSync(value: Boolean) {
        settingsStorage.setAutoSync(value)
    }

    fun setAppTheme(value: Themes) {
        settingsStorage.setAppTheme(value)
    }

    fun getVersion(): String {
        return BuildConfig.VERSION_NAME
    }
}
