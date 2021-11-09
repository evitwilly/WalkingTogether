package ru.freeit.walkingtogether.presentation.screens.auth

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.freeit.walkingtogether.core.CoroutineViewModel

class RegisterViewModel(
    private val id: String,
    private val savedState: SavedStateHandle,
    private val database: MyFirebaseDatabase
) : CoroutineViewModel() {

    private val avatarIdKey = "avatar_id"

    private val images = AvatarImages()
    private val registerState = MutableLiveData<RegisterState>()
    private val checkedAvatar = MutableLiveData<AvatarImage>()

    fun init() {
        val avatarId = savedState.get<Int>(avatarIdKey) ?: -1
        if (avatarId != -1) {
            this.checkedAvatar.value = images.drawableBy(avatarId)
        } else {
            selectAvatar(images.randomFemale().id())
        }
    }

    fun observeAvatar(lifecycleOwner: LifecycleOwner, observer: Observer<AvatarImage>) = checkedAvatar.observe(lifecycleOwner, observer)
    fun observeRegisterState(lifecycleOwner: LifecycleOwner, observer: Observer<RegisterState>) = registerState.observe(lifecycleOwner, observer)

    private fun saveCheckedAvatar() { savedState[avatarIdKey] = checkedAvatar.value?.id() ?: -1 }

    fun selectAvatar(id: Int) {
        checkedAvatar.value = images.drawableBy(id)
        saveCheckedAvatar()
    }

    fun checkFemale() {
        checkedAvatar.value = images.randomFemale()
        saveCheckedAvatar()
    }

    fun checkMale() {
        checkedAvatar.value = images.randomMale()
        saveCheckedAvatar()
    }

    fun register(isFemale: Boolean, name: String, bio: String) {

        if (name.trim().isEmpty()) {
            registerState.value = RegisterState.NameEmpty
            return
        }

        if (bio.trim().isEmpty()) {
            registerState.value = RegisterState.BioEmpty
            return
        }

        val avatar = checkedAvatar.value!!

        viewModelScope.launch {
            try {
                registerState.value = RegisterState.Loading
                withContext(Dispatchers.IO) {
                    database.add(FirebaseUser(id, name, bio, isFemale, avatar.id()))
                }
                registerState.value = RegisterState.Success
            } catch (exc: Exception) {
                registerState.value = RegisterState.Failure
            }
        }
    }

}