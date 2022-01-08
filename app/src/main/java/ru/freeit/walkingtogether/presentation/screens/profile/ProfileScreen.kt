package ru.freeit.walkingtogether.presentation.screens.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.freeit.walkingtogether.core.App
import ru.freeit.walkingtogether.core.AppSharedPreferences
import ru.freeit.walkingtogether.core.data.AppSharedPreferences
import ru.freeit.walkingtogether.core.viewmodel.CoroutineViewModel
import ru.freeit.walkingtogether.data.firebasedb.MyFirebaseDatabase
import ru.freeit.walkingtogether.data.firebasedb.entity.FirebaseUser

import ru.freeit.walkingtogether.databinding.ProfileScreenBinding
import ru.freeit.walkingtogether.domain.entity.User
import ru.freeit.walkingtogether.presentation.screens.intro.MyNavigator
import ru.freeit.walkingtogether.presentation.screens.register.AvatarImages
import ru.freeit.walkingtogether.presentation.screens.register.AvatarListDialog
import ru.freeit.walkingtogether.presentation.screens.register.AvatarListDialogListener

sealed class LoginState {
    object Logged : LoginState()
    object None : LoginState()
}

class ProfileViewModelFactory(private val appPrefs: AppSharedPreferences, private val database: MyFirebaseDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProfileViewModel(appPrefs, database) as T
    }
}

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

    fun selectAvatar(id: Int) {
        // TODO don't forget about this method
        val newAvatar = images.drawableBy(id)
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    user?.value?.toFirebase()?.copy(avatarId = newAvatar.id())?.let { firebaseUser ->
                        firebaseUser.save(appPrefs)
                        database.add(firebaseUser)
                    }

                }
                user.value = user?.value?.copy(avatar = newAvatar)
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

class ProfileScreen : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = ProfileScreenBinding.inflate(inflater, container, false)

        val factory = (requireActivity().application as App).viewModelFactories.profile()
        val viewModel = ViewModelProvider(this, factory).get(ProfileViewModel::class.java)

        val navigator = MyNavigator(parentFragmentManager)

        viewModel.observeUser(viewLifecycleOwner) { user ->
            user.apply {
                img(binding.avatarImage)
                name(binding.nameText)
            }
        }

        binding.avatarChangeButton.setOnClickListener {
            AvatarListDialog.newInstance(viewModel.isFemale()).show(parentFragmentManager)
        }

        AvatarListDialogListener(parentFragmentManager).listen(viewLifecycleOwner, viewModel::selectAvatar)

        binding.logoutButton.setOnClickListener {
            viewModel.logout()
        }

        viewModel.observe(viewLifecycleOwner) { loginState ->
            if (loginState == LoginState.None) {
                navigator.intro()
            }
        }

        viewModel.init()

        return binding.root
    }

}