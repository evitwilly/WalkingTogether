package ru.freeit.walkingtogether.presentation.screens.intro

import android.app.Activity
import android.content.Context
import androidx.lifecycle.*
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.freeit.walkingtogether.core.AppSharedPreferences
import ru.freeit.walkingtogether.core.CoroutineViewModel
import ru.freeit.walkingtogether.core.GoogleAuthData
import ru.freeit.walkingtogether.core.GoogleSignClient
import ru.freeit.walkingtogether.data.firebasedb.MyFirebaseDatabase
import ru.freeit.walkingtogether.data.firebasedb.entity.FirebaseUser

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