package ru.freeit.walkingtogether.presentation.screens.register

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.freeit.walkingtogether.core.data.AppSharedPreferences
import ru.freeit.walkingtogether.core.viewmodel.CoroutineViewModel
import ru.freeit.walkingtogether.data.firebasedb.MyFirebaseDatabase
import ru.freeit.walkingtogether.data.firebasedb.entity.FirebaseUser

class RegisterOptionsUi(
    private val isFemale: Boolean,
    private val name: String,
    private val bio: String
) {

    fun emptyName() = name.isBlank()
    fun emptyBio() = bio.isBlank()
    fun firebaseUser(id: String, avatarId: Int) = FirebaseUser(id, name, bio, isFemale, avatarId)
}

class RegisterViewModel(
    private val id: String,
    private val savedState: SavedStateHandle,
    private val appPrefs: AppSharedPreferences,
    private val database: MyFirebaseDatabase
) : CoroutineViewModel() {

    private val avatarIdKey = "avatar_id"

    private val images = AvatarImages()
    private val registerState = MutableLiveData<RegisterState>()
    private val checkedAvatar = MutableLiveData<AvatarImage>()

    init {
        if (id.isBlank()) {
            registerState.value = RegisterState.GoogleError
        }
    }

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
        if (images.isFemale(checkedAvatar.value)) {
            return
        }
        checkedAvatar.value = images.randomFemale()
        saveCheckedAvatar()
    }

    fun checkMale() {
        if (images.isMale(checkedAvatar.value)) {
            return
        }
        checkedAvatar.value = images.randomMale()
        saveCheckedAvatar()
    }

    fun register(reg: RegisterOptionsUi) {
        if (reg.emptyName()) {
            registerState.value = RegisterState.NameEmpty; return
        }
        if (reg.emptyBio()) {
            registerState.value = RegisterState.BioEmpty; return
        }

        val avatar = checkedAvatar.value ?: return

        viewModelScope.launch {
            try {
                registerState.value = RegisterState.Loading
                withContext(Dispatchers.IO) {
                    val user = reg.firebaseUser(id, avatar.id())
                    user.save(appPrefs)
                    database.add(user)
                }
                registerState.value = RegisterState.Success
            } catch (exc: Exception) {
                registerState.value = RegisterState.Failure
            }
        }
    }

}