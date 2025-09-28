package com.rentalvehicalfleet.app.utils

/**
 * Data handler class, responsible to handle data.
 */
class ApplicationDataHandler {

    // Vehicle data
    private var vehicleDataHashMap : HashMap<String, String> = HashMap<String, String> ()
    // Speed data
    private var speedDataHashMap : HashMap<String, Int> = HashMap<String, Int> ()

    fun setVehicleData(key: RentalVehicleType, value: String){
        vehicleDataHashMap.put(key.toString(), value)
    }

    fun getCarData(key: String): String {
        return vehicleDataHashMap.get(key) ?: "DEFAULT"
    }

    fun setCarSpeed(key: String, value: Int) {
        speedDataHashMap.put(key, value)
    }

    fun getCarSpeed(key: String): Int {
       return speedDataHashMap.get(key) ?: 0
    }
}