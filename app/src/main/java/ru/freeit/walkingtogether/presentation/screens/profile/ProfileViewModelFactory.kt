package ru.freeit.walkingtogether.presentation.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.freeit.walkingtogether.data.firebasedb.UserFirebaseDatabase
import ru.freeit.walkingtogether.data.firebasedb.entity.LocalUserRepository
import ru.freeit.walkingtogether.presentation.base.AvatarImages

class ProfileViewModelFactory(
    private val userRepo: LocalUserRepository,
    private val images: AvatarImages,
    private val database: UserFirebaseDatabase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProfileViewModel(userRepo, images, database) as T
    }
}