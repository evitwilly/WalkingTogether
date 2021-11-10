package ru.freeit.walkingtogether.presentation.screens.register

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import ru.freeit.walkingtogether.data.firebasedb.MyFirebaseDatabase

class RegisterViewModelFactory(
    private val id: String,
    private val database: MyFirebaseDatabase,
    owner: SavedStateRegistryOwner,
    bundle: Bundle?
) : AbstractSavedStateViewModelFactory(owner, bundle) {
    override fun <T : ViewModel?> create(key: String, modelClass: Class<T>, handle: SavedStateHandle) =
        RegisterViewModel(id, handle, database) as T
}