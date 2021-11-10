package ru.freeit.walkingtogether.presentation.screens.intro

import androidx.fragment.app.FragmentManager
import ru.freeit.walkingtogether.R
import ru.freeit.walkingtogether.presentation.screens.register.RegisterScreen

class MyNavigator(private val manager: FragmentManager) {
    fun register(id: String) {
        manager.beginTransaction()
            .replace(R.id.fragment_container, RegisterScreen.newInstance(id))
            .addToBackStack(null)
            .commit()
    }
}