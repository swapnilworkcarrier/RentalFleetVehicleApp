package com.rentalvehicalfleet.app.repository

/**
 * Interface for Vehicle repository.
 */
interface IVehicleSpeedRepository {
    suspend fun getDefaultSpeed(vehId: String): Int?
    suspend fun getSpeedLimitForCar(vehId: String): Int?
    suspend fun setSpeedLimitForCar(vehId: String, maxSpeed: Int)
}