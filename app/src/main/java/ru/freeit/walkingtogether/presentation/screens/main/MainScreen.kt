package ru.freeit.walkingtogether.presentation.screens.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.freeit.walkingtogether.R
import ru.freeit.walkingtogether.core.App
import ru.freeit.walkingtogether.core.AppSharedPreferences
import ru.freeit.walkingtogether.core.CoroutineViewModel
import ru.freeit.walkingtogether.data.firebasedb.entity.FirebaseUser

import ru.freeit.walkingtogether.databinding.MainScreenBinding
import ru.freeit.walkingtogether.presentation.MainActivity
import ru.freeit.walkingtogether.presentation.screens.intro.MyNavigator

class WalkingViewModelFactory(private val appPrefs: AppSharedPreferences) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WalkingViewModel(appPrefs) as T
    }
}

sealed class LoginState {
    object Logged : LoginState()
    object None : LoginState()
}

class WalkingViewModel(private val appPrefs: AppSharedPreferences) : CoroutineViewModel() {

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

}

class MainScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = MainScreenBinding.inflate(inflater)

        val appPrefs = (requireActivity().application as App).appPrefs
        binding.userText.text = FirebaseUser.restore(appPrefs).toString()

        val navigator = MyNavigator(parentFragmentManager)

        val factory = (requireActivity().application as App).viewModelFactories.walking()
        val viewModel = ViewModelProvider(this, factory).get(WalkingViewModel::class.java)

        binding.signOutButton.setOnClickListener {
            viewModel.logout()
        }

        viewModel.observe(viewLifecycleOwner) { loginState ->
            if (loginState == LoginState.None) {
                navigator.intro()
            }
        }

        return binding.root
    }
}