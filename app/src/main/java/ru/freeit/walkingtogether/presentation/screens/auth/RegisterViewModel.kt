package ru.freeit.walkingtogether.presentation.screens.auth

import android.os.Bundle
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import kotlinx.coroutines.launch
import ru.freeit.walkingtogether.core.CoroutineViewModel

class RegisterViewModelFactory(
    private val id: String,
    private val database: MyFirebaseDatabase,
    owner: SavedStateRegistryOwner,
    bundle: Bundle?
) : AbstractSavedStateViewModelFactory(owner, bundle) {
    override fun <T : ViewModel?> create(key: String, modelClass: Class<T>, handle: SavedStateHandle) =
        RegisterViewModel(id, handle, database) as T
}

sealed class RegisterState {
    object NameEmpty : RegisterState()
    object BioEmpty : RegisterState()
    object Success : RegisterState()
    object Failure : RegisterState()
}

class RegisterViewModel(
    private val id: String,
    private val savedState: SavedStateHandle,
    private val database: MyFirebaseDatabase
) : CoroutineViewModel() {

    private val images = AvatarImages()
    private val registerState = MutableLiveData<RegisterState>()
    private val checkedAvatar = MutableLiveData<Int>()

    fun init() {
        val checkedAvatar = savedState.get<Int>("checked_avatar") ?: -1
        if (checkedAvatar != -1) {
            this.checkedAvatar.value = checkedAvatar
        } else {
            this.checkedAvatar.value = images.randomFemale()
        }
    }

    fun observeAvatar(lifecycleOwner: LifecycleOwner, observer: Observer<Int>) = checkedAvatar.observe(lifecycleOwner, observer)
    fun observeRegisterState(lifecycleOwner: LifecycleOwner, observer: Observer<RegisterState>) = registerState.observe(lifecycleOwner, observer)

    fun resetRegisterState() { registerState.value = null }

    private fun saveCheckedAvatar() { savedState["checked_avatar"] = checkedAvatar.value ?: -1 }

    fun selectAvatar(drawableId: Int) {
        checkedAvatar.value = drawableId
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

        val avatarResourceId = checkedAvatar.value!!
        viewModelScope.launch {
            try {
                database.add(FirebaseUser(id, name, bio, isFemale, avatarResourceId))
                registerState.value = RegisterState.Success
            } catch (exc: Exception) {
                registerState.value = RegisterState.Failure
            }
        }
    }

}