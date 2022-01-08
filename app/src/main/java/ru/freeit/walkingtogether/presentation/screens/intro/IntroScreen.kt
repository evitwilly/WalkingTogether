package ru.freeit.walkingtogether.presentation.screens.intro

import android.app.Activity.RESULT_CANCELED
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import ru.freeit.walkingtogether.R
import ru.freeit.walkingtogether.core.App
import ru.freeit.walkingtogether.core.delegates.viewBinding
import ru.freeit.walkingtogether.core.extensions.color
import ru.freeit.walkingtogether.core.extensions.logoText
import ru.freeit.walkingtogether.databinding.IntroScreenBinding
import ru.freeit.walkingtogether.presentation.disable
import ru.freeit.walkingtogether.presentation.enable

abstract class BaseFragment(@LayoutRes layoutRes: Int) : Fragment(layoutRes) {
    protected val factories by lazy {
        (requireActivity().application as App).viewModelFactories
    }
}

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

        val googleSignInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            if (activityResult.resultCode == RESULT_CANCELED) {
                return@registerForActivityResult
            }
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(activityResult.data)
            try {
                val account = task.getResult(ApiException::class.java)
                viewModel.check(account.id!!)
            } catch (e: ApiException) {
                e.printStackTrace()
                Snackbar.make(binding.root, R.string.missing_internet, Snackbar.LENGTH_SHORT).show()
                binding.loginButton.enable()
            }
        }

        binding.loginButton.setOnClickListener {
            binding.loginButton.disable()
            googleSignInLauncher.launch(viewModel.signInIntent())
        }
    }
}