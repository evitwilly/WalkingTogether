package ru.freeit.walkingtogether.presentation.screens.intro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import ru.freeit.walkingtogether.R
import ru.freeit.walkingtogether.core.App
import ru.freeit.walkingtogether.databinding.IntroScreenBinding

class IntroScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = IntroScreenBinding.inflate(inflater, container, false)

        val factory = (requireActivity().application as App).viewModelFactories.intro(requireActivity())
        val viewModel = ViewModelProvider(this, factory).get(IntroViewModel::class.java).apply {
            signOut()
        }

        val navigator = MyNavigator(parentFragmentManager)

        viewModel.observeUserState(viewLifecycleOwner) { userState ->
            when (userState) {
                is UserState.Success -> {
                   navigator.map()
                }
                is UserState.None -> {
                    navigator.register(userState.id())
                }
            }
        }

        val googleSignInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(activityResult.data)
            try {
                val account = task.getResult(ApiException::class.java)
                viewModel.check(account.id!!)
            } catch (e: ApiException) {
                Snackbar.make(binding.root, R.string.missing_internet, Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.loginButton.setOnClickListener { googleSignInLauncher.launch(viewModel.signInIntent()) }

        return binding.root
    }

}