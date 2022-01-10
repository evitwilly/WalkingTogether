package ru.freeit.walkingtogether.presentation.screens.register

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import ru.freeit.walkingtogether.data.firebasedb.UserFirebaseDatabase
import ru.freeit.walkingtogether.data.firebasedb.entity.LocalUserRepository
import ru.freeit.walkingtogether.presentation.base.AvatarImages

class RegisterViewModelFactory(
    private val id: String,
    private val database: UserFirebaseDatabase,
    private val userRepo: LocalUserRepository,
    private val images: AvatarImages,
    owner: SavedStateRegistryOwner,
    bundle: Bundle?
) : AbstractSavedStateViewModelFactory(owner, bundle) {
    override fun <T : ViewModel?> create(key: String, modelClass: Class<T>, handle: SavedStateHandle) =
        RegisterViewModel(id, handle, userRepo, images, database) as T
}