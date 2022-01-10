package ru.freeit.walkingtogether.presentation.base

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.freeit.walkingtogether.presentation.screens.map.MapScreen
import ru.freeit.walkingtogether.presentation.screens.profile.ProfileScreen
import ru.freeit.walkingtogether.presentation.screens.walk.WalkScreen

class NavHostAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val fragments = listOf(
        MapScreen::class.java,
        ProfileScreen::class.java,
        WalkScreen::class.java
    )

    override fun getItemCount() = fragments.size
    override fun createFragment(position: Int): Fragment = fragments[position].newInstance()

}