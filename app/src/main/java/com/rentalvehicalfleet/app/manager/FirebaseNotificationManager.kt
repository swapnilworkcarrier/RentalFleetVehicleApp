package com.rentalvehicalfleet.app.manager

import android.util.Log

/**
 * This is notification class responsible for sending notification via Firebase.
 */
class FirebaseNotificationManager: INotificationManager {

    companion object {
        private const val TAG: String = "FirebaseNotificationManager"
    }

    override fun sendNotification(title: String, message: String, vehId: String) {
        // Add Notification logic here.
        Log.d(TAG,"Notification Sent $title, $message")
    }
}