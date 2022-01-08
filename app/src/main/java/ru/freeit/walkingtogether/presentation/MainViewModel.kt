package ru.freeit.walkingtogether.presentation

import androidx.lifecycle.ViewModel
import ru.freeit.walkingtogether.core.AppSharedPreferences
import ru.freeit.walkingtogether.data.firebasedb.entity.FirebaseUser

class MainViewModel(appPrefs: AppSharedPreferences) : ViewModel() {

    private val user = FirebaseUser.restore(appPrefs)

    fun isLogin() = user.isExists()

}