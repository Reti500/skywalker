package com.ricardgo.android.skywalker.services

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.util.Log
import com.ricardgo.android.skywalker.WalkerApplication

class LocationService : Service(), LocationListener {

    companion object {
        const val routeIdExtra = "routeId"
    }

    private val locationInterval = 500L
    private val TAG = "L-Track"
    private var lManager : LocationManager? = null
    private var location : Location? = null
    private var handler: Handler? = null
    private var gpsEnabled: Boolean? = null
    private var networkEnable : Boolean? = null

    override fun onCreate() {
        super.onCreate()

        handler = Handler()
        trackLocation()
    }

    override fun onBind(p0: Intent?): IBinder? {
        Log.e(TAG, "ON bind")

        return null
    }

    override fun onLocationChanged(l: Location?) {
        Log.e(TAG, "Location change: lat:${l?.latitude}, lng:${l?.longitude}")
    }

    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
        Log.e(TAG, "Status change $p0")
    }

    override fun onProviderEnabled(p0: String?) {
        Log.e(TAG, "ON provider enable")
    }

    override fun onProviderDisabled(p0: String?) {
        Log.e(TAG, "ON provider disabled")
    }

    private fun trackLocation() {
        handler?.postDelayed({

            getLocation()

            if (WalkerApplication.prefs.trackLocation) {
                trackLocation()
            } else {
                handler?.removeCallbacksAndMessages(null)
                lManager?.removeUpdates(this)
                stopSelf()
            }
        }, locationInterval)
    }

    private fun getLocation() {
        lManager = applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        gpsEnabled = lManager?.isProviderEnabled(LocationManager.GPS_PROVIDER)
        networkEnable = lManager?.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        when {
            gpsEnabled == true -> requestFromGPS()
            networkEnable == true -> requestFromNetwork()
            else -> Log.e(TAG, "Not provider for get Location")
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestFromGPS() {
        Log.e(TAG, "Reques from GPS")
        location = null
        lManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, locationInterval, 0.0f, this)
        lManager?.also {
            location = it.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            location?.also { l ->
                Log.e(TAG, "Location: lat:${l.latitude}, lng:${l.longitude}")
            }
        }
    }

    private fun requestFromNetwork() {
        Log.e(TAG, "Reques from Network")
    }
}