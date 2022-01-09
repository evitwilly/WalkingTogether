package ru.freeit.walkingtogether.presentation

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import ru.freeit.walkingtogether.R
import ru.freeit.walkingtogether.core.delegates.viewBinding
import ru.freeit.walkingtogether.databinding.FragmentNavHostBinding

class NavHostFragment : Fragment() {
    private val binding by viewBinding(FragmentNavHostBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuId = arguments?.getInt(menuIdKey) ?: R.id.maps

        binding.bottomNavigation.setOnItemSelectedListener { selectedItem ->
            when (selectedItem.itemId) {
                R.id.maps -> { binding.viewPager2.currentItem = 0 }
                R.id.profile -> { binding.viewPager2.currentItem = 1 }
                R.id.walkings -> { binding.viewPager2.currentItem = 2 }
            }
            true
        }
        binding.bottomNavigation.selectedItemId = menuId

    }

    companion object {
        private const val menuIdKey = "menu_id_key"
        fun newInstance(menuId: Int) = NavHostFragment().apply {
            arguments = bundleOf(menuIdKey to menuId)
        }
    }

}