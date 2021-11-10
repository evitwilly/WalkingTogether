package ru.freeit.walkingtogether.core

import android.content.Context
import android.os.Bundle
import androidx.savedstate.SavedStateRegistryOwner
import ru.freeit.walkingtogether.data.firebasedb.MyFirebaseDatabase
import ru.freeit.walkingtogether.presentation.MainViewModelFactory
import ru.freeit.walkingtogether.presentation.screens.intro.IntroViewModel
import ru.freeit.walkingtogether.presentation.screens.intro.IntroViewModelFactory
import ru.freeit.walkingtogether.presentation.screens.register.RegisterViewModelFactory

class ViewModelFactories(
    private val database: MyFirebaseDatabase,
    private val appPrefs: AppSharedPreferences
) {
    fun register(id: String, owner: SavedStateRegistryOwner, bundle: Bundle?) = RegisterViewModelFactory(id, database, appPrefs, owner, bundle)
    fun intro(ctx: Context) = IntroViewModelFactory(ctx, appPrefs, database)
    fun main() = MainViewModelFactory(appPrefs)
}