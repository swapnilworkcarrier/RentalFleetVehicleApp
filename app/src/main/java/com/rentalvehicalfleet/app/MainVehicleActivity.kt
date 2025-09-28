package com.rentalvehicalfleet.app

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.rentalvehicalfleet.app.manager.NotificationManager
import com.rentalvehicalfleet.app.model.IVehicleSpeedChangeListener
import com.rentalvehicalfleet.app.repository.VehicleSpeedRepository
import com.rentalvehicalfleet.app.utils.ApplicationDataHandler
import com.rentalvehicalfleet.app.utils.Constants
import com.rentalvehicalfleet.app.utils.NotificationsUtil
import com.rentalvehicalfleet.app.utils.RentalVehicleType
import com.rentalvehicalfleet.app.viewmodel.FleetVehicleViewModel

/**
 * This is main vehicle activity, visible to driver. It is responsible for showing notification,
 * manage vehicle data, speed data through view model.
 */
class MainVehicleActivity : AppCompatActivity() {

    companion object {
        private const val TAG: String = "MainVehicleActivity"
    }

    private var applicationDataHandler: ApplicationDataHandler = ApplicationDataHandler()

    private var notificationManager: NotificationManager = NotificationManager()

    private var vehicleSpeedRepository: VehicleSpeedRepository =
        VehicleSpeedRepository(applicationDataHandler, notificationManager)

    private var fleetVehicleViewModel: FleetVehicleViewModel =
        FleetVehicleViewModel(vehicleSpeedRepository, applicationDataHandler)

    // Speed listener optional in activity
    private val speedListener: IVehicleSpeedChangeListener = fleetVehicleViewModel

    private var textView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

         /*
         * Initialise notification firebase manager or AWS manger by passing true/false.
         * This will determine by which service is live now.
         * Assuming Firebase notification is ON now.This value later on change by Fleet Owner
         * */
        notificationManager.initNotificationManager(true)
        initViews()
        initialDataSetup()
        initObservers()
    }

   /*
   * Initialize VehiclePropertyManager & register VehiclePropertyCallback
   * */
    private fun initVehiclePropertyManager() {
        fleetVehicleViewModel.initVehiclePropertyManager(this)
        fleetVehicleViewModel.registerFleetVehiclePropertyChangeCallBack()
    }

    private fun initViews() {
        textView = findViewById<TextView>(R.id.currentSpeedTextView)
    }

    private fun initialDataSetup() {
        // Set Vehicle id and fleet id/ group id.
        applicationDataHandler.setVehicleData(RentalVehicleType.VEHICLE_ID, Constants.VEH_ID)

        // Rental company should sets default speed limit for rental vehicle group
        fleetVehicleViewModel.getDefaultSpeed(Constants.VEH_ID)

        // Rental agent sets a specific speed limit for a vehicle.
        fleetVehicleViewModel.setMaxSpeedLimit(Constants.VEH_ID, Constants.MAX_SPEED)

        // Get speed limit set for a vehicle by agent.
        fleetVehicleViewModel.getMaxSpeedLimit(Constants.VEH_ID)
    }

    private fun initObservers() {
        // Observe the speed change.
        fleetVehicleViewModel.speedLiveData.observe(this) { speed ->
            // Update current vehicle speed to UI
            textView?.text = "Vehicle Speed: $speed"
        }

        // Show speed limit exceed warning pop up.
        fleetVehicleViewModel.speedLimitExceededLiveData.observe(this) { exceeded ->
            if (exceeded) {
                showAlert()
            }
        }
    }


    override fun onStart() {
        super.onStart()
        initVehiclePropertyManager()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "On Destroy")
    }

    private fun showAlert() {
        // on screen notification.
        NotificationsUtil.buildNotification(
            context = this,
            title = getString(R.string.speed_alert_title),
            description = getString(R.string.speed_alert_message),
        )
    }
}