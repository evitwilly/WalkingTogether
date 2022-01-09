package ru.freeit.walkingtogether.presentation.screens.profile

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.freeit.walkingtogether.core.data.AppSharedPreferences
import ru.freeit.walkingtogether.core.viewmodel.CoroutineViewModel
import ru.freeit.walkingtogether.data.firebasedb.MyFirebaseDatabase
import ru.freeit.walkingtogether.data.firebasedb.entity.FirebaseUser
import ru.freeit.walkingtogether.domain.entity.User
import ru.freeit.walkingtogether.presentation.screens.register.AvatarImages

class ProfileViewModel(private val appPrefs: AppSharedPreferences, private val database: MyFirebaseDatabase) : CoroutineViewModel() {

    private val images = AvatarImages()
    private val user = MutableLiveData<User>()

    private val loginState = MutableLiveData<LoginState>(LoginState.Logged)

    fun observe(owner: LifecycleOwner, observer: Observer<LoginState>) = loginState.observe(owner, observer)

    fun logout() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                FirebaseUser.logout(appPrefs)
            }
            loginState.value = LoginState.None
        }
    }

    fun removeAccount(isRemoving: Boolean) {
        if (isRemoving) {
            logout()
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    database.remove(user.value!!.toFirebase())
                }
                loginState.value = LoginState.None
            }
        }
    }

    fun selectAvatar(id: Int) {
        // TODO don't forget about this method
        val newAvatar = images.drawableBy(id)
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    user.value?.toFirebase()?.copy(avatarId = newAvatar.id())
                        ?.let { firebaseUser ->
                            firebaseUser.save(appPrefs)
                            database.add(firebaseUser)
                        }

                }
                user.value = user.value?.copy(avatar = newAvatar)
            } catch (someException: Exception) {
                // TODO make errors handling
            }
        }
    }

    fun observeUser(owner: LifecycleOwner, observer: Observer<User>) = user.observe(owner, observer)

    fun isFemale() = user.value?.isFemale() ?: true

    fun init() {
        viewModelScope.launch {
            val firebaseUser = FirebaseUser.restore(appPrefs)
            user.value = firebaseUser.toDomain(images)
        }
    }
}