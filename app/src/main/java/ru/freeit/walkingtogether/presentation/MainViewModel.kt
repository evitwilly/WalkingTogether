package ru.freeit.walkingtogether.presentation

import androidx.lifecycle.ViewModel
import ru.freeit.walkingtogether.core.data.AppSharedPreferences
import ru.freeit.walkingtogether.data.firebasedb.entity.FirebaseUser
import ru.freeit.walkingtogether.data.firebasedb.entity.LocalUserRepository

class MainViewModel(repo: LocalUserRepository) : ViewModel() {

    private val user = repo.read()

    fun isLogin() = user.isExists()

}