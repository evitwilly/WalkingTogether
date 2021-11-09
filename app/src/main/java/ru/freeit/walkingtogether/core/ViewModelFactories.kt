package ru.freeit.walkingtogether.core

import android.os.Bundle
import androidx.savedstate.SavedStateRegistryOwner
import ru.freeit.walkingtogether.data.firebasedb.MyFirebaseDatabase
import ru.freeit.walkingtogether.presentation.screens.register.RegisterViewModelFactory

class ViewModelFactories(
    private val database: MyFirebaseDatabase
) {
    fun register(id: String, owner: SavedStateRegistryOwner, bundle: Bundle?) =
        RegisterViewModelFactory(id, database, owner, bundle)
}