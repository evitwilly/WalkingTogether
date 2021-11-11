package ru.freeit.walkingtogether.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.selects.select
import ru.freeit.walkingtogether.R
import ru.freeit.walkingtogether.core.App
import ru.freeit.walkingtogether.data.firebasedb.entity.FirebaseUser
import ru.freeit.walkingtogether.databinding.ActivityMainBinding
import ru.freeit.walkingtogether.presentation.screens.intro.IntroScreen
import ru.freeit.walkingtogether.presentation.screens.intro.MyNavigator
import ru.freeit.walkingtogether.presentation.screens.main.MainScreen
import ru.freeit.walkingtogether.presentation.screens.map.MapScreen

fun Button.disable() {
    this.isEnabled = false
}

fun Button.enable() {
    this.isEnabled = true
}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factories = (application as App).viewModelFactories
        val viewModel = ViewModelProvider(this, factories.main()).get(MainViewModel::class.java)

        val navigator = MyNavigator(supportFragmentManager)

        supportFragmentManager.addFragmentOnAttachListener { _, fragment ->
            binding.bottomNavigation.isVisible = fragment is MainScreen || fragment is MapScreen
        }

        binding.bottomNavigation.setOnItemSelectedListener { selectedItem ->
            when (selectedItem.itemId) {
                R.id.maps -> {

                }
                R.id.profile -> {

                }
                R.id.walkings -> {

                }
            }
            true
        }

        if (savedInstanceState == null) {
            val isLogin = viewModel.isLogin()
            binding.bottomNavigation.isVisible = isLogin
            if (isLogin) {
                navigator.map()
            } else {
                navigator.intro()
            }
        }
    }
}