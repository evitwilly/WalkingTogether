package ru.freeit.walkingtogether.presentation.screens.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.freeit.walkingtogether.core.location.LastKnownLocation

class MapViewModelFactory(private val lastKnownLocation: LastKnownLocation) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MapViewModel(lastKnownLocation) as T
    }
}