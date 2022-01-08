package ru.freeit.walkingtogether.presentation.screens.intro

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.freeit.walkingtogether.core.data.AppSharedPreferences
import ru.freeit.walkingtogether.data.firebasedb.MyFirebaseDatabase

class IntroViewModelFactory(
    private val ctx: Context,
    private val appPrefs: AppSharedPreferences,
    private val database: MyFirebaseDatabase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return IntroViewModel(ctx, appPrefs, database) as T
    }
}