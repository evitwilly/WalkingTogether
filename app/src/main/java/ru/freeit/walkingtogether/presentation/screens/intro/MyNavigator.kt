package ru.freeit.walkingtogether.presentation.screens.intro

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import ru.freeit.walkingtogether.R
import ru.freeit.walkingtogether.presentation.screens.main.MainScreen
import ru.freeit.walkingtogether.presentation.screens.map.MapScreen
import ru.freeit.walkingtogether.presentation.screens.register.RegisterScreen

class MyNavigator(private val manager: FragmentManager) {
    private val containerId = R.id.fragment_container

    fun register(id: String) {
        manager.beginTransaction()
            .replace(containerId, RegisterScreen.newInstance(id))
            .addToBackStack(null)
            .commit()
    }

    fun map() {
        clear()
        replace(MapScreen())
//        manager.beginTransaction()
//            .replace(containerId, MapScreen())
//            .commit()
    }

    fun intro() {
        clear()
        replace(IntroScreen())
//        manager.beginTransaction()
//            .replace(R.id.fragment_container, IntroScreen())
//            .commit()
    }

    fun main() {
        clear()
        replace(MainScreen())
//        manager.beginTransaction()
//            .replace(containerId, MainScreen())
//            .commit()
    }

    private fun replace(fragment: Fragment) {
        manager.beginTransaction()
            .replace(containerId, fragment)
            .commit()
    }

    fun back() = manager.popBackStack()

    private fun clear() = manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
}