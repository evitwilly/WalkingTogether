package ru.freeit.walkingtogether.presentation.screens.map

import androidx.lifecycle.ViewModel
import ru.freeit.walkingtogether.core.location.LastKnownLocation

class MapViewModel(private val lastKnownLocation: LastKnownLocation) : ViewModel() {

    fun defineLocation() {
        val location = lastKnownLocation.location()
        if (location == null) {
            // TODO location is null
        } else {
            // TODO all goods
        }
    }

}