package com.rentalvehicalfleet.app.manager

/**
 * This is Notification Manager class.
 */
interface INotificationManager {

    // Send Notification.
    fun sendNotification(title: String, message: String, vehId: String)
}
