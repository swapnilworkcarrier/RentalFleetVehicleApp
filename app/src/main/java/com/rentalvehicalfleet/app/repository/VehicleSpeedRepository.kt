package com.rentalvehicalfleet.app.repository

import com.rentalvehicalfleet.app.manager.NotificationManager
import com.rentalvehicalfleet.app.utils.ApplicationDataHandler
import com.rentalvehicalfleet.app.utils.Constants

/**
 * Vehicle repository class, responsible for managing data.
 */
open class VehicleSpeedRepository(
    val applicationDataHandler: ApplicationDataHandler,
    val notificationManager: NotificationManager
) : IVehicleSpeedRepository {

    // Send a notification for over speed vehicle.
    fun sendNotificationToCompany(title: String, message: String, vehId: String) {
        notificationManager.sendNotification(title, message, vehId)
    }

    // Get default speed for rental car group.
    override suspend fun getDefaultSpeed(vehId: String): Int? {
        applicationDataHandler.setCarSpeed(vehId, Constants.DEFAULT_MAX_SPEED)
        return applicationDataHandler.getCarSpeed(vehId)
    }

    //  Get speed limit for rental vehicle group.
    override suspend fun getSpeedLimitForCar(vehId: String): Int? {
        val maxSpeed = applicationDataHandler.getCarSpeed(vehId)
        return if (maxSpeed == 0) applicationDataHandler.getCarSpeed(vehId) else maxSpeed
    }

    // Sets Speed limit.
    override suspend fun setSpeedLimitForCar(vehId: String, maxSpeed: Int) {
        applicationDataHandler.setCarSpeed(vehId, maxSpeed)
    }
}