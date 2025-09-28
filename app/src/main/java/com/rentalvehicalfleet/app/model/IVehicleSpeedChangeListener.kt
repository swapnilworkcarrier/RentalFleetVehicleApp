package com.rentalvehicalfleet.app.model

/**
 * Interface for speed change observation“.
 */
interface IVehicleSpeedChangeListener {
    /**
     * Speed change event.
     */
    fun onSpeedChange(currentSpeed: Int)
}
