package com.rentalvehicalfleet.app.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rentalvehicalfleet.app.model.IVehicleSpeedChangeListener
import com.rentalvehicalfleet.app.model.VehiclePropertyUseCase
import com.rentalvehicalfleet.app.model.VehiclePropertyUseCaseImp
import com.rentalvehicalfleet.app.repository.VehicleSpeedRepository
import com.rentalvehicalfleet.app.utils.ApplicationDataHandler
import com.rentalvehicalfleet.app.utils.RentalVehicleType
import kotlinx.coroutines.launch

/**
 *  Activity View Model for MainCar activity.
 */
class FleetVehicleViewModel(
    private val repository: VehicleSpeedRepository,
    private val applicationDataHandler: ApplicationDataHandler
) : ViewModel(), IVehicleSpeedChangeListener {

    companion object {
        private const val TAG: String = "FleetVehicleViewModel"
    }

    private var maxSpeedLimit: Int? = 0
    val speedLiveData = MutableLiveData<Int>()
    val speedLimitExceededLiveData = MutableLiveData<Boolean>()
    private val carPropertyUseCase: VehiclePropertyUseCase = VehiclePropertyUseCaseImp()

    /**
     * Get default Speed for rental Vehicle group.
     */
    fun getDefaultSpeed(vehId: String) {
        viewModelScope.launch {
            val defaultSpeedLimit = repository.getDefaultSpeed(vehId)
            Log.d(TAG, "Default Speed: $defaultSpeedLimit")
        }
    }

    /**
     * Get max speed limit for Vehicle.
     */
    fun getMaxSpeedLimit(vehId: String) {
        viewModelScope.launch {
            maxSpeedLimit = repository.getSpeedLimitForCar(vehId)
            Log.d(TAG, "Max Speed: $maxSpeedLimit")
        }
    }

    /**
     * Set max speed limit for Vehicle.
     */
    fun setMaxSpeedLimit(vehId: String, maxSpeed: Int) {
        viewModelScope.launch {
            repository.setSpeedLimitForCar(vehId, maxSpeed)
        }
    }

    override fun onSpeedChange(currentSpeed: Int) {
        val maxSpeed: Int = maxSpeedLimit ?: 0
        speedLiveData.postValue(currentSpeed)
        Log.d(TAG, "Speed Limit: $maxSpeed , Vehicle Speed:$currentSpeed")
        if (currentSpeed > maxSpeed) {
            speedLimitExceededLiveData.postValue(true)
            val vehId: String = applicationDataHandler.getCarData(RentalVehicleType.VEHICLE_ID.name)

            // Sending notification
            repository.sendNotificationToCompany(
                title = "Speed Limit Exceeded.",
                message = "Vehicle : $vehId has exceeded the speed limit.",
                vehId = vehId
            )
        } else {
            speedLimitExceededLiveData.postValue(false)
        }
    }
    /*
    * Initialize CarPropertyManager
    * */
    fun initVehiclePropertyManager(context: Context) {
        carPropertyUseCase.initiateCarPropertyManager(context)
    }

    /*
    * Register CarPropertyManager for callback
    * */
    fun registerFleetVehiclePropertyChangeCallBack() {
        carPropertyUseCase.registerPropertyChangeCallBack()
        carPropertyUseCase.startObservingCarSpeed(this)
    }
}