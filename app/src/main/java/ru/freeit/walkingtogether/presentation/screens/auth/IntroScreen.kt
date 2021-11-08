package ru.freeit.walkingtogether.presentation.screens.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import ru.freeit.walkingtogether.R
import ru.freeit.walkingtogether.databinding.IntroScreenBinding

class IntroScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = IntroScreenBinding.inflate(inflater, container, false)

        val account = GoogleUserData(requireContext())

        val googleSignClient = GoogleSignClient(requireActivity())

        if (account.isLogin()) {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, RegisterScreen.newInstance(account.id()))
                .addToBackStack(null)
                .commit()
        }

        val googleSignInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(activityResult.data)
            try {
                val account = task.getResult(ApiException::class.java)

                val googleAccountId = account.id!!

                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, RegisterScreen.newInstance(googleAccountId))
                    .addToBackStack(null)
                    .commit()

            } catch (e: ApiException) {
                Snackbar.make(binding.root, R.string.missing_internet, Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.loginButton.setOnClickListener {
            googleSignInLauncher.launch(googleSignClient.signInIntent())
        }

        return binding.root
    }

}