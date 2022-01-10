package ru.freeit.walkingtogether.presentation.screens.intro

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.freeit.walkingtogether.core.data.AppSharedPreferences
import ru.freeit.walkingtogether.core.google.GoogleSignClient
import ru.freeit.walkingtogether.data.firebasedb.UserFirebaseDatabase
import ru.freeit.walkingtogether.data.firebasedb.entity.LocalUserRepository

class IntroViewModelFactory(
    private val googleSignClient: GoogleSignClient,
    private val repo: LocalUserRepository,
    private val database: UserFirebaseDatabase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return IntroViewModel(googleSignClient, repo, database) as T
    }
}