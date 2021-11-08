package ru.freeit.walkingtogether.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.freeit.walkingtogether.R
import ru.freeit.walkingtogether.presentation.screens.auth.IntroScreen
import ru.freeit.walkingtogether.presentation.screens.auth.RegisterScreen

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, IntroScreen())
                .commit()
        }
    }
}