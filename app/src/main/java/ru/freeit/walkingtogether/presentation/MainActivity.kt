package ru.freeit.walkingtogether.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import ru.freeit.walkingtogether.core.App
import ru.freeit.walkingtogether.databinding.ActivityMainBinding
import ru.freeit.walkingtogether.presentation.base.MyNavigator

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factories = (application as App).viewModelFactories
        val viewModel = ViewModelProvider(this, factories.main()).get(MainViewModel::class.java)

        val navigator = MyNavigator(supportFragmentManager)

//        supportFragmentManager.addFragmentOnAttachListener { _, fragment ->
//            binding.bottomNavigation.isVisible = navigator.isBottomNavigation(fragment)
//        }
//
//        binding.bottomNavigation.setOnItemSelectedListener { selectedItem ->
//            when (selectedItem.itemId) {
//                R.id.maps -> { navigator.map() }
//                R.id.profile -> { navigator.profile() }
//                R.id.walkings -> { navigator.walk() }
//            }
//            true
//        }

        if (savedInstanceState == null) {
            val isLogin = viewModel.isLogin()
//            binding.bottomNavigation.isVisible = isLogin
            if (isLogin) {
                navigator.main()
            } else {
                navigator.intro()
            }
        }
    }
}