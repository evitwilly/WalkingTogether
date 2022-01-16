package ru.freeit.walkingtogether.presentation.screens.map


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import ru.freeit.walkingtogether.R
import ru.freeit.walkingtogether.core.delegates.viewBinding
import ru.freeit.walkingtogether.databinding.MapScreenBinding

class MapScreen : AbstractMapScreen(R.layout.map_screen) {

    private val binding by viewBinding(MapScreenBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.mapView = binding.mapView
        binding.mapView.onCreate(savedInstanceState)


    }

}