package com.example.finalfinalspace.services

import android.content.Context
import com.example.finalfinalspace.data.db.models.QuotesInfo
import com.example.finalfinalspace.di.qualifiers.IoDispatcher
import com.example.finalfinalspace.domain.QuotesManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class PushNotificationService : FirebaseMessagingService() {

    @Inject lateinit var quotesManager: QuotesManager
    @IoDispatcher lateinit var ioDispatcher: CoroutineDispatcher

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Timber.tag("newToken").e(token)
        getSharedPreferences("_", MODE_PRIVATE).edit().putString("fb", token).apply()
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        try {
            val id = message.data.getValue("id")
            val quote = message.data.getValue("quote")
            val by = message.data.getValue("by")
            val quoteInfo = QuotesInfo(id.toInt(), quote, by)
            GlobalScope.launch(ioDispatcher) {
                quotesManager.addQuote(quoteInfo)
            }
        } catch (_: Exception) {
        }
    }

    fun getToken(context: Context): String? {
        return context.getSharedPreferences("_", MODE_PRIVATE).getString("fb", "empty")
    }
}
