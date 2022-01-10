package ru.freeit.walkingtogether.core

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.google.firebase.FirebaseApp
import ru.freeit.walkingtogether.core.data.AppSharedPreferences
import ru.freeit.walkingtogether.core.viewmodel.ViewModelFactories
import ru.freeit.walkingtogether.data.firebasedb.FirebaseCoreDatabase
import ru.freeit.walkingtogether.data.firebasedb.UserFirebaseDatabase
import ru.freeit.walkingtogether.data.firebasedb.entity.LocalUserRepository
import ru.freeit.walkingtogether.presentation.base.AvatarImages

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    private val coreDatabase by lazy { FirebaseCoreDatabase.Base() }
    private val userDatabase by lazy { UserFirebaseDatabase.Base(coreDatabase) }

    private val appPrefs by lazy { AppSharedPreferences.Base(this) }
    private val userRepo by lazy { LocalUserRepository.Base(appPrefs) }
    val images by lazy { AvatarImages.Base() }

    val viewModelFactories by lazy { ViewModelFactories(userDatabase, userRepo, images) }

}