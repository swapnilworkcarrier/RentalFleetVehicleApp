package com.rentalvehicalfleet.app.model

import android.content.Context


interface VehiclePropertyUseCase {

    /**
     * Init Vehicle Property Manager.
     */
    fun initiateCarPropertyManager(context: Context)

    /**
     * Start observing Vehicle speed change event.
     */
    fun startObservingCarSpeed(listener: IVehicleSpeedChangeListener)

    /**
     *  Register call back.
     */
    fun registerPropertyChangeCallBack()
}