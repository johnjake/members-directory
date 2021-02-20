package com.github.members.directory.features.main

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Bundle
import androidx.core.app.NotificationCompat
import com.github.members.directory.R
import com.github.members.directory.features.splash.SplashActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import timber.log.Timber


class FirebaseService: FirebaseMessagingService() {

    lateinit var gson: Gson

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Timber.e(" $token")
        //Add your token in your sharepreferences.
        getSharedPreferences("_", MODE_PRIVATE).edit().putString("fcm_token", token).apply()
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Timber.e("reading remote message...")
        Timber.e("collapseKey: ${remoteMessage.collapseKey}")
        Timber.e("data: ${remoteMessage.data}")
        Timber.e("from: ${remoteMessage.from}")
        Timber.e("to: ${remoteMessage.to}")
        Timber.e("messageId: ${remoteMessage.messageId}")
        Timber.e("messageType: ${remoteMessage.messageType}")
        val title = remoteMessage.notification?.title ?: ""
        val body = remoteMessage.notification?.body ?: ""
        Timber.e("Notification Title: $title")
        Timber.e("Notification Body: $body")

        addNotification(title, body)

        Timber.e("reading data...")
        if (remoteMessage.data.isNotEmpty()) {
            remoteMessage.data.forEach { (key, value) ->
                Timber.d("$key: $value")
            }
        }

    }

    //Whenewer you need FCM token, just call this static method to get it.
    fun getToken(context: Context): String? {
        return context.getSharedPreferences("_", MODE_PRIVATE).getString("fcm_token", "empty")
    }

    private fun showNotification(
        title: String = application.getString(R.string.app_name),
        messageBody: String? = null,
        notificationId: Int = 0,
        customIntent: Intent? = null,
        extras: Bundle? = null,
        channelId: String = application.getString(R.string.default_notification_channel_id)
    ) {
        val intent: Intent

        Timber.d("notificationId: $notificationId")

        if (customIntent != null) {
            Timber.d("FCM -> customIntent")
            intent = customIntent
        } else {
            Timber.d("FCM -> defaultIntent")
            intent = Intent(this, SplashActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }

        val pendingIntent = PendingIntent.getActivity(
            this, notificationId, intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_github)
            .setContentTitle(title)
            .setContentText(messageBody)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(messageBody)
            )
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        extras?.let {
            notificationBuilder.extras = it
        }

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(notificationId, notificationBuilder.build())
    }

    private fun addNotification(
        title: String, content: String, channelId: String = application.getString(
            R.string.default_notification_channel_id
        )
    ) {
        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.instafood)
            .setContentTitle(title)
            .setContentText(content)
        val notificationIntent = Intent(this, SplashActivity::class.java)
        val contentIntent = PendingIntent.getActivity(
            this, 0, notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        builder.setContentIntent(contentIntent)

        // Add as notification
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(FM_NOTIFICATION_ID, builder.build())
    }

    // Remove notification
    private fun removeNotification() {
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.cancel(FM_NOTIFICATION_ID)
    }

    fun heads_up_notification(channelId: String = application.getString(R.string.default_notification_channel_id)) {
        val mBuilder = NotificationCompat.Builder(this, channelId)
        val nNotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val piDismiss = PendingIntent.getActivity(
            this, 0, Intent(
                this,
                SplashActivity::class.java
            ), 0
        )
        val snoozeIntent = Intent(this, MainActivity::class.java)
        snoozeIntent.action = NotificationCompat.CATEGORY_ALARM
        val piSnooze = PendingIntent.getService(this, 0, snoozeIntent, 0)
        mBuilder.setSmallIcon(R.drawable.ic_github)
        mBuilder.setContentTitle("Heads up Notification")
        mBuilder.setContentText("heads up activated")
        mBuilder.setDefaults(-1)
        mBuilder.setPriority(1)
        mBuilder.addAction(R.drawable.ic_menu_save, "Dismiss", piDismiss)
        mBuilder.addAction(R.drawable.ic_arrow_24dp, "Stop", piSnooze)
        nNotificationManager.notify(2, mBuilder.build())
    }

    companion object {
        const val FM_NOTIFICATION_ID = 54643345
    }
}