package ru.freeit.walkingtogether.core

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.google.firebase.FirebaseApp
import ru.freeit.walkingtogether.presentation.screens.auth.MyFirebaseDatabase

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    private val firebaseDatabase by lazy { MyFirebaseDatabase() }

    val viewModelFactories by lazy { ViewModelFactories(firebaseDatabase) }

}