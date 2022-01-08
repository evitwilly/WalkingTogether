package ru.freeit.walkingtogether.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.freeit.walkingtogether.core.data.AppSharedPreferences

class MainViewModelFactory(private val appPrefs: AppSharedPreferences) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(appPrefs) as T
    }
}