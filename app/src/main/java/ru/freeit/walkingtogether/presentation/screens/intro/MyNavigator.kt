package ru.freeit.walkingtogether.presentation.screens.intro

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import ru.freeit.walkingtogether.R

import ru.freeit.walkingtogether.presentation.screens.map.MapScreen
import ru.freeit.walkingtogether.presentation.screens.profile.ProfileScreen
import ru.freeit.walkingtogether.presentation.screens.register.RegisterScreen
import ru.freeit.walkingtogether.presentation.screens.walk.WalkScreen

class MyNavigator(private val manager: FragmentManager) {

    private val containerId = R.id.fragment_container

    fun register(id: String) {
        replace(RegisterScreen.newInstance(id), "register")
    }

    fun isBottomNavigation(fragment: Fragment) =
            fragment is MapScreen ||
            fragment is ProfileScreen ||
            fragment is WalkScreen

    fun map() {
        clear()
        replace(MapScreen())
    }

    fun profile() {
        clear()
        replace(ProfileScreen())
    }

    fun walk() {
        clear()
        replace(WalkScreen())
    }

    fun intro() {
        clear()
        replace(IntroScreen())
    }

    private fun replace(fragment: Fragment, backStackName: String? = null) {
        val transaction = manager.beginTransaction().replace(containerId, fragment)

        if (backStackName != null) {
            transaction.addToBackStack(backStackName)
        }

        transaction.commit()
    }

    fun back() = manager.popBackStack()

    private fun clear() = manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
}