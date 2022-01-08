package ru.freeit.walkingtogether.presentation.screens.intro

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import ru.freeit.walkingtogether.R
import ru.freeit.walkingtogether.core.delegates.viewBinding
import ru.freeit.walkingtogether.core.extensions.logoText
import ru.freeit.walkingtogether.databinding.IntroScreenBinding
import ru.freeit.walkingtogether.presentation.disable
import ru.freeit.walkingtogether.presentation.enable

class IntroScreen : BaseFragment(R.layout.intro_screen) {

    private val binding by viewBinding(IntroScreenBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(this, factories.intro(requireActivity())).get(IntroViewModel::class.java)

        val navigator = MyNavigator(parentFragmentManager)

        binding.logoText.logoText()

        viewModel.observeUserState(viewLifecycleOwner) { userState ->
            when (userState) {
                is UserState.Success -> {
                    navigator.map()
                }
                is UserState.None -> {
                    navigator.register(userState.id())
                }
            }
            binding.loginButton.enable()
        }

        val googleAccountLogin = GoogleAccountLogin(this)
        googleAccountLogin.onSuccess(viewModel::check)
        googleAccountLogin.onCancel(binding.loginButton::enable)
        googleAccountLogin.onError {
            Snackbar.make(binding.root, R.string.missing_internet, Snackbar.LENGTH_SHORT).show()
            binding.loginButton.enable()
        }

        binding.loginButton.setOnClickListener {
            binding.loginButton.disable()
            googleAccountLogin.login(viewModel.signInIntent())
        }
    }

}