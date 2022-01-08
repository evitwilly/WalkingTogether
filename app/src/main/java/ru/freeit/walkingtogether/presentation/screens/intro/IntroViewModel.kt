package ru.freeit.walkingtogether.presentation.screens.intro

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.freeit.walkingtogether.core.data.AppSharedPreferences
import ru.freeit.walkingtogether.core.google.GoogleSignClient
import ru.freeit.walkingtogether.core.viewmodel.CoroutineViewModel
import ru.freeit.walkingtogether.data.firebasedb.MyFirebaseDatabase

class IntroViewModel(
    ctx: Context,
    private val appPrefs: AppSharedPreferences,
    private val database: MyFirebaseDatabase
) : CoroutineViewModel() {

    private val googleSignClient = GoogleSignClient(ctx)

    private val userState = MutableLiveData<UserState>()

    init {
        googleSignClient.signOut()
        userState.value = null
    }

    fun observeUserState(owner: LifecycleOwner, observer: Observer<UserState>) = userState.observe(owner, observer)

    fun check(id: String) {
        viewModelScope.launch {
            val user = withContext(Dispatchers.IO) {
                val user = database.user(id)
                if (user.isExists()) {
                    user.save(appPrefs)
                }
                user
            }
            userState.value = if (user.isExists()) UserState.Success else UserState.None(id)
        }
    }
//
//    fun signOut() {
//        googleSignClient.signOut()
//        userState.value = null
//    }

    fun signInIntent() = googleSignClient.signInIntent()

}