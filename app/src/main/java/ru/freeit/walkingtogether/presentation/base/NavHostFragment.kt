package ru.freeit.walkingtogether.presentation.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.freeit.walkingtogether.R
import ru.freeit.walkingtogether.core.delegates.viewBinding
import ru.freeit.walkingtogether.databinding.FragmentNavHostBinding

class NavHostFragment : Fragment(R.layout.fragment_nav_host) {

    private val binding by viewBinding(FragmentNavHostBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewPager2.adapter = NavHostAdapter(this)
        binding.viewPager2.isUserInputEnabled = false

        binding.bottomNavigation.setOnItemSelectedListener { selectedItem ->
            when (selectedItem.itemId) {
                R.id.maps -> { binding.viewPager2.setCurrentItem(0, false) }
                R.id.profile -> { binding.viewPager2.setCurrentItem(1, false) }
                R.id.walkings -> { binding.viewPager2.setCurrentItem(2, false) }
            }
            true
        }

    }

}