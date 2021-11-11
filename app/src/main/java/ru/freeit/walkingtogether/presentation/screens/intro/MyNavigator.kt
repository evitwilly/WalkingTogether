package ru.freeit.walkingtogether.presentation.screens.intro

import androidx.fragment.app.FragmentManager
import ru.freeit.walkingtogether.R
import ru.freeit.walkingtogether.presentation.screens.main.MainScreen
import ru.freeit.walkingtogether.presentation.screens.register.RegisterScreen

class MyNavigator(private val manager: FragmentManager) {
    private val containerId = R.id.fragment_container

    fun register(id: String) {
        manager.beginTransaction()
            .replace(containerId, RegisterScreen.newInstance(id))
            .addToBackStack(null)
            .commit()
    }
    fun intro() {
        manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        manager.beginTransaction()
            .replace(R.id.fragment_container, IntroScreen())
            .commit()
    }
    fun main() {
        manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        manager.beginTransaction()
            .replace(containerId, MainScreen())
            .commit()
    }

    fun back() = manager.popBackStack()
}