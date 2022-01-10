package ru.freeit.walkingtogether.presentation.screens.name

import android.widget.EditText
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.launch
import ru.freeit.walkingtogether.core.viewmodel.CoroutineViewModel
import ru.freeit.walkingtogether.data.firebasedb.UserFirebaseDatabase
import ru.freeit.walkingtogether.data.firebasedb.entity.LocalUserRepository
import ru.freeit.walkingtogether.presentation.screens.name.ui.NameEditState

class NameViewModel(
    private val userRepo: LocalUserRepository,
    private val database: UserFirebaseDatabase
) : CoroutineViewModel() {

    private val currentUser = userRepo.read()
    private val editState = MutableLiveData<NameEditState>()

    fun fillTextFields(nameEdit: EditText, bioEdit: EditText) {
        nameEdit.setText(currentUser.name())
        bioEdit.setText(currentUser.desc())
    }

    fun observe(lifecycleOwner: LifecycleOwner, observer: Observer<NameEditState>) = editState.observe(lifecycleOwner, observer)

    fun apply(name: String, bio: String) {
        if (name.isBlank()) {
            editState.value = NameEditState.NameEmpty; return
        }

        viewModelScope.launch {
            editState.value = NameEditState.Loading

            val newUser = currentUser.copy(name = name, bio = bio)

            userRepo.save(newUser)
            database.add(newUser)

            editState.value = NameEditState.Success
        }
    }

}