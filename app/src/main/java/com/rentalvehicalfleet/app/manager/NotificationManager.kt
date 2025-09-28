package com.rentalvehicalfleet.app.manager

/**
 * This is notification manager, manage to send notification.
 */
class NotificationManager {

    private var notificationManager: INotificationManager? = null

    fun initNotificationManager(isFireBase: Boolean) {
        if (isFireBase) {
            // Firebase notification manager.
            notificationManager = FirebaseNotificationManager()
        } else {
            // AWS notification manager.
            notificationManager = AWSNotificationManager()
        }
    }

    // Send notification via manager.
    fun sendNotification(title: String, message: String, vehId: String) {
        notificationManager?.sendNotification(title,message,vehId)
    }
}