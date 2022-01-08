package ru.freeit.walkingtogether.presentation.screens.intro

import android.content.Context
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.freeit.walkingtogether.core.AppSharedPreferences
import ru.freeit.walkingtogether.core.viewmodel.CoroutineViewModel
import ru.freeit.walkingtogether.core.google.GoogleSignClient
import ru.freeit.walkingtogether.data.firebasedb.MyFirebaseDatabase

sealed class UserState {
    object Success : UserState()
    data class None(
        private val id: String
    ) : UserState() {
        fun id() = id
    }
}

class IntroViewModelFactory(
    private val ctx: Context,
    private val appPrefs: AppSharedPreferences,
    private val database: MyFirebaseDatabase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return IntroViewModel(ctx, appPrefs, database) as T
    }
}

class IntroViewModel(
    ctx: Context,
    private val appPrefs: AppSharedPreferences,
    private val database: MyFirebaseDatabase
) : CoroutineViewModel() {

    private val googleSignClient = GoogleSignClient(ctx)

    private val userState = MutableLiveData<UserState>()

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

    fun signOut() {
        googleSignClient.signOut()
        userState.value = null
    }

    fun signInIntent() = googleSignClient.signInIntent()

}