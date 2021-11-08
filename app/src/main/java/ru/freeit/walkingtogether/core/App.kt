package ru.freeit.walkingtogether.core

import android.app.Application
import com.google.firebase.FirebaseApp
import ru.freeit.walkingtogether.presentation.screens.auth.FirebaseDatabase

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }

    val firebaseDatabase by lazy { FirebaseDatabase() }
}