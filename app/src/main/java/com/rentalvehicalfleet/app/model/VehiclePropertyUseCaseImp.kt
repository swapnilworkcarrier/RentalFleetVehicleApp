package com.rentalvehicalfleet.app.model

import android.content.Context
import android.util.Log
import kotlin.In
import android.car.Car
import android.car.VehiclePropertyIds
import android.car.hardware.CarPropertyValue
import android.car.hardware.property.CarPropertyManager

/**
 * Dummy implementation for Car Property Manager.
 */
class VehiclePropertyUseCaseImp : VehiclePropertyUseCase {

    companion object {
        private const val TAG: String = "VehiclePropertyUseCaseImp"
    }

    private var vehPropertyManager: CarPropertyManager? = null
    private var speedListener: IVehicleSpeedChangeListener? = null


    override fun initiateCarPropertyManager(context: Context) {
       // Initiate CarPropertyManager here
        vehPropertyManager = (CarPropertyManager) Car.createCar(context).getCarManager(Car.PROPERTY_SERVICE);
    }

    override fun startObservingCarSpeed(listener: IVehicleSpeedChangeListener) {
        // Receive Speed change event.
        speedListener = listener
    }

    override fun registerPropertyChangeCallBack() {
       // Register property change call back.
        vehPropertyManager.registerCallback(object : CarPropertyEventCallback() {
            override fun onChangeEvent(carPropertyValue: CarPropertyValue) {
                Log.d(
                    TAG,
                    ("PERF_VEHICLE_SPEED: onChangeEvent(" + carPropertyValue.getValue()).toString() + ")"
                )
                // when speed change event will be sent
                speedListener.onSpeedChange(carPropertyValue.getValue())
            }

            override fun onErrorEvent(propId: Int, zone: Int) {
                Log.d(TAG, "PERF_VEHICLE_SPEED: onErrorEvent($propId, $zone)")
            }
        }, VehiclePropertyIds.PERF_VEHICLE_SPEED, CarPropertyManager.SENSOR_RATE_NORMAL)
    }
}