package ru.freeit.walkingtogether.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.freeit.walkingtogether.core.AppSharedPreferences
import ru.freeit.walkingtogether.data.firebasedb.entity.FirebaseUser

class MainViewModelFactory(private val appPrefs: AppSharedPreferences) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(appPrefs) as T
    }
}

class MainViewModel(appPrefs: AppSharedPreferences) : ViewModel() {

    private val user = FirebaseUser.restore(appPrefs)

    fun isLogin() = user.isExists()

}