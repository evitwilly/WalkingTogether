package ru.freeit.walkingtogether.core.location

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.lifecycle.*

fun interface LocationCallback {
    fun onLocation(location: Location?)
}

class LocationService(lifecycleOwner: LifecycleOwner) : DefaultLifecycleObserver {

    private var locationManager : LocationManager? = null

    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    private var locationListener : LocationListener? = null

    @SuppressLint("MissingPermission")
    fun start(ctx: Context, onLocationCallback: (Location?) -> Unit) {
        if (locationListener == null || locationManager == null) {
            locationListener = LocationListener(onLocationCallback)
            locationManager = ctx.getSystemService(LOCATION_SERVICE) as LocationManager
            startLocationUpdates()
        }
    }

    @SuppressLint("MissingPermission")
    fun stop() {
        locationListener?.let { listener ->
            locationManager?.removeUpdates(listener)
        }
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        locationListener?.let { listener ->
            val isNetworkEnabled = locationManager?.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ?: false
            val isGpsEnabled = locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) ?: false
            when {
                isNetworkEnabled -> {
                    locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000L, 10f, listener)
                }
                isGpsEnabled -> {
                    locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000L, 10f, listener)
                }
                else -> {}
            }
        }
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        startLocationUpdates()
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        stop()
    }

}