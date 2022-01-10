package ru.freeit.walkingtogether.presentation.screens.profile

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.freeit.walkingtogether.core.viewmodel.CoroutineViewModel
import ru.freeit.walkingtogether.data.firebasedb.UserFirebaseDatabase
import ru.freeit.walkingtogether.data.firebasedb.entity.FirebaseUser
import ru.freeit.walkingtogether.data.firebasedb.entity.LocalUserRepository
import ru.freeit.walkingtogether.domain.entity.UserDomain
import ru.freeit.walkingtogether.presentation.screens.register.AvatarImages


class ProfileViewModel(
    private val userRepo: LocalUserRepository,
    private val images: AvatarImages,
    private val database: UserFirebaseDatabase
) : CoroutineViewModel() {

    private val user = MutableLiveData<UserDomain>()

    private val loginState = MutableLiveData<LoginState>(LoginState.Logged)

    fun observe(owner: LifecycleOwner, observer: Observer<LoginState>) = loginState.observe(owner, observer)
    fun observeUser(owner: LifecycleOwner, observer: Observer<UserDomain>) = user.observe(owner, observer)

    fun logout() {
        viewModelScope.launch {
            userRepo.remove()
            loginState.value = LoginState.None
        }
    }

    fun removeAccount(isRemoving: Boolean) {
        if (isRemoving) {
            logout()
            viewModelScope.launch {
                database.remove(user.value!!.toFirebase())
                loginState.value = LoginState.None
            }
        }
    }

    fun selectAvatar(id: Int) {
        val newAvatar = images.drawableBy(id)
        val newUser = user.value?.copy(avatar = newAvatar)

        val lastUser = user.value
        user.value = newUser

        viewModelScope.launch {
            try {
                newUser?.toFirebase()?.let { firebaseUser ->
                    userRepo.save(firebaseUser)
                    database.add(firebaseUser)
                }
            } catch (someException: Exception) {
                user.value = lastUser
                // TODO handling error!
            }
        }
    }

    fun isFemale() = user.value?.isFemale() ?: true

    fun init() {
        viewModelScope.launch {
            user.value = userRepo.read().toDomain(images)
        }
    }

}