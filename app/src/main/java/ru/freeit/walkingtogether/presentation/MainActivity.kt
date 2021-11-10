package ru.freeit.walkingtogether.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import ru.freeit.walkingtogether.R
import ru.freeit.walkingtogether.core.App
import ru.freeit.walkingtogether.data.firebasedb.entity.FirebaseUser
import ru.freeit.walkingtogether.presentation.screens.intro.IntroScreen
import ru.freeit.walkingtogether.presentation.screens.intro.MyNavigator

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val factories = (application as App).viewModelFactories
        val viewModel = ViewModelProvider(this, factories.main()).get(MainViewModel::class.java)

        val navigator = MyNavigator(supportFragmentManager)

        if (savedInstanceState == null) {
            if (viewModel.isLogin()) {
                navigator.main()
            } else {
                navigator.intro()
            }
        }
    }
}