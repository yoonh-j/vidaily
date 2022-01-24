package com.yoond.vidaily.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.PushToken
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.yoond.vidaily.MainActivity
import com.yoond.vidaily.R

class FCMService: FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        registerPushToken(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        val notification = remoteMessage.notification
        if (notification != null) {
            val title = notification.title!!
            val body = notification.body!!
            createNotificationChannel()
            notify(title, body)
        }
    }

    private fun registerPushToken(pushToken: String) {
        val uid = Amplify.Auth.currentUser.userId
        val token = PushToken.builder()
            .token(pushToken)
            .id(uid)
            .build()

        Amplify.API.mutate(
            ModelMutation.update(token),
            { Log.i("FCM_SERVICE", "updatePushToken succeeded: $it") },
            { Log.e("FCM_SERVICE", "updatePushToken failed", it) }
        )
    }

    private fun createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTI_CHANNEL_ID,
                NOTI_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = NOTI_CHANNEL_DESCRIPTION

            val notiManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notiManager.createNotificationChannel(channel)
        }
    }

    private fun notify(title: String, body: String) {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(this, NOTI_ID, intent, PendingIntent.FLAG_ONE_SHOT)
        val notification = NotificationCompat.Builder(this, NOTI_CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .build()

        NotificationManagerCompat.from(this).notify(NOTI_ID, notification)
    }

    companion object {
        private const val NOTI_CHANNEL_ID = "NOTIFICATION_CHANNEL_ID"
        private const val NOTI_ID = 1000
        private const val NOTI_CHANNEL_NAME = "NOTIFICATION_CHANNEL_NAME"
        private const val NOTI_CHANNEL_DESCRIPTION = "NOTIFICATION_CHANNEL_DESCRIPTION"
    }
}