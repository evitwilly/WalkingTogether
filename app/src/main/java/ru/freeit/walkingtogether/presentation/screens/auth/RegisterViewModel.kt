package ru.freeit.walkingtogether.presentation.screens.auth

import android.os.Bundle
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RegisterViewModelFactory(private val id: String, owner: SavedStateRegistryOwner, bundle: Bundle?) : AbstractSavedStateViewModelFactory(owner, bundle) {
    override fun <T : ViewModel?> create(key: String, modelClass: Class<T>, handle: SavedStateHandle) =
        RegisterViewModel(id, handle) as T
}

sealed class RegisterResult {
    object NameEmpty : RegisterResult()
    object BioEmpty : RegisterResult()
    object Success : RegisterResult()
    object Error : RegisterResult()
}

class RegisterViewModel(private val id: String, private val savedState: SavedStateHandle) : ViewModel() {

    private val images = AvatarImages()
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

    private fun saveCheckedAvatar() {
        savedState["checked_avatar"] = checkedAvatar.value ?: -1
    }

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
        val avatarResourceId = checkedAvatar.value!!
    }

}