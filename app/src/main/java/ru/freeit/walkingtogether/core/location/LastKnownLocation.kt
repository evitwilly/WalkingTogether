package ru.freeit.walkingtogether.core.location

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager

class LastKnownLocation(ctx: Context) {

    private val locationManager = ctx.getSystemService(LOCATION_SERVICE) as LocationManager

    @SuppressLint("MissingPermission")
    fun location() : Location? {

        val providers = locationManager.getProviders(true)

        var bestLocation: Location? = null

        providers.forEach { provider ->
            val location = locationManager.getLastKnownLocation(provider)
            if (location != null) {
                if (bestLocation == null || (location.accuracy > bestLocation!!.accuracy)) {
                    bestLocation = location
                }
            }
        }

        return bestLocation
    }




}