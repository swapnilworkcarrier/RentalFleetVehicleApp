package com.rentalvehicalfleet.app.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.rentalvehicalfleet.app.MainVehicleActivity
import com.rentalvehicalfleet.app.R

/**
 * This is notification util class.
 */
internal object NotificationsUtil {

    private const val CHANNEL_ID = "speed_demo"
    private const val CHANNEL_NAME = "speed_demo_project_name"
    fun createNotificationChannel(): NotificationChannel {
        // create the notification channel
        return NotificationChannel(
            CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT
        )
    }

    fun getNotificationManager(context: Context) =
        context.getSystemService(Service.NOTIFICATION_SERVICE) as NotificationManager

    fun buildForegroundNotification(
        context: Context,
        title: String,
        description: String,
        isOngoing: Boolean = false,
        autoCancel: Boolean = false
    ): Notification {
        return NotificationCompat.Builder(context, CHANNEL_ID).setContentTitle(title)
            .setContentText(description).setOngoing(isOngoing).setAutoCancel(autoCancel)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setForegroundServiceBehavior(NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE)
            .setContentIntent(Intent(
                context, MainVehicleActivity::class.java
            ).let { notificationIntent ->
                PendingIntent.getActivity(
                    context, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE
                )
            }).build()
    }

    fun buildNotification(context: Context, title: String, description: String) {
        val channel = createNotificationChannel()
        getNotificationManager(context).createNotificationChannel(channel)

        val builder = Notification.Builder(context, CHANNEL_ID).setContentTitle(title)
            .setContentText(description).setSmallIcon(R.drawable.ic_launcher_background)
            .setContentIntent(Intent(
                context, MainVehicleActivity::class.java
            ).let { notificationIntent ->
                PendingIntent.getActivity(
                    context, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE
                )
            })
        getNotificationManager(context).notify(1234, builder.build())
    }
}