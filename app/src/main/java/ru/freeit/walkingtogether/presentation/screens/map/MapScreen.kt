package ru.freeit.walkingtogether.presentation.screens.map


import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import ru.freeit.walkingtogether.R
import ru.freeit.walkingtogether.core.delegates.viewBinding
import ru.freeit.walkingtogether.core.location.LocationPermission
import ru.freeit.walkingtogether.core.location.LocationService
import ru.freeit.walkingtogether.databinding.MapScreenBinding

class MapScreen : AbstractMapScreen(R.layout.map_screen) {

    private val binding by viewBinding(MapScreenBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.mapView = binding.mapView
        binding.mapView.onCreate(savedInstanceState)

        val viewModel = ViewModelProvider(this, factories.map()).get(MapViewModel::class.java)

        val locationService = LocationService(viewLifecycleOwner)

        fun startServices() {
            locationService.start(ctx) { location ->
                // TODO send to our ViewModel
            }
            viewModel.defineLocation()
        }

        val locationPermission = LocationPermission()
        locationPermission.listen(this, ::startServices) {
            // TODO Permission is DENIED
        }

        fun requestPermission() = locationPermission.requestPermission(requireContext(), ::startServices)

        requestPermission()


    }

}