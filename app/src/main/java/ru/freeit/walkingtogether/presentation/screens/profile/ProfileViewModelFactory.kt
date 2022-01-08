package ru.freeit.walkingtogether.presentation.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.freeit.walkingtogether.core.data.AppSharedPreferences
import ru.freeit.walkingtogether.data.firebasedb.MyFirebaseDatabase

class ProfileViewModelFactory(private val appPrefs: AppSharedPreferences, private val database: MyFirebaseDatabase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProfileViewModel(appPrefs, database) as T
    }
}