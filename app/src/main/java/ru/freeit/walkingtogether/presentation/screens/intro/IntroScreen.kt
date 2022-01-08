package ru.freeit.walkingtogether.presentation.screens.intro

import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.Shape
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannedString
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
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
import ru.freeit.walkingtogether.presentation.disable
import ru.freeit.walkingtogether.presentation.enable

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

        binding.logoText.text = SpannableString(getString(R.string.app_name)).apply {
            setSpan(ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.purple_300)), 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            setSpan(ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.purple_800)), 4, 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            setSpan(RelativeSizeSpan(1.5f), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            setSpan(RelativeSizeSpan(1.5f), 2, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            setSpan(RelativeSizeSpan(1.5f), 3, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            setSpan(RelativeSizeSpan(1.2f), 5, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            setSpan(RelativeSizeSpan(1.2f), 6, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

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
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(activityResult.data)
            try {
                val account = task.getResult(ApiException::class.java)
                viewModel.check(account.id!!)
            } catch (e: ApiException) {
                Snackbar.make(binding.root, R.string.missing_internet, Snackbar.LENGTH_SHORT).show()
                binding.loginButton.enable()
            }
        }

        binding.loginButton.setOnClickListener {
            binding.loginButton.disable()
            googleSignInLauncher.launch(viewModel.signInIntent())
        }

        return binding.root
    }

}