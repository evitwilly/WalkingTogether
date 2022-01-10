package ru.freeit.walkingtogether.presentation.screens.intro

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.freeit.walkingtogether.core.data.AppSharedPreferences
import ru.freeit.walkingtogether.core.google.GoogleSignClient
import ru.freeit.walkingtogether.core.viewmodel.CoroutineViewModel
import ru.freeit.walkingtogether.data.firebasedb.UserFirebaseDatabase
import ru.freeit.walkingtogether.data.firebasedb.entity.LocalUserRepository

class IntroViewModel(
    private val googleSignClient: GoogleSignClient,
    private val userRepo: LocalUserRepository,
    private val database: UserFirebaseDatabase
) : CoroutineViewModel(), SignInIntent {

    private val userState = MutableLiveData<UserState>()

    fun signOut() {
        googleSignClient.signOut()
        userState.value = null
    }

    fun observeUserState(owner: LifecycleOwner, observer: Observer<UserState>) = userState.observe(owner, observer)

    fun check(id: String) {
        viewModelScope.launch {
            val user = database.user(id)

            userRepo.save(user)

            userState.value = if (user.isExists()) UserState.Success else UserState.None(id)
        }
    }

    override fun intent() = googleSignClient.signInIntent()

}