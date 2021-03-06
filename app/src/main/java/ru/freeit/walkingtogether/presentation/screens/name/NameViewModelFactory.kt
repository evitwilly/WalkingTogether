package ru.freeit.walkingtogether.presentation.screens.name

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.freeit.walkingtogether.data.firebasedb.UserFirebaseDatabase
import ru.freeit.walkingtogether.data.firebasedb.entity.LocalUserRepository

class NameViewModelFactory(
    private val userRepo: LocalUserRepository,
    private val database: UserFirebaseDatabase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NameViewModel(userRepo, database) as T
    }
}