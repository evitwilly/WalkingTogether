package ru.freeit.walkingtogether.presentation.screens.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.freeit.walkingtogether.databinding.MainScreenBinding

class MainScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = MainScreenBinding.inflate(inflater)

        return binding.root
    }
}