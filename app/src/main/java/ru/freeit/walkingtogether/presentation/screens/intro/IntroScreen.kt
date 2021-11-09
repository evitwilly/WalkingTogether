package ru.freeit.walkingtogether.presentation.screens.intro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import ru.freeit.walkingtogether.R
import ru.freeit.walkingtogether.databinding.IntroScreenBinding
import ru.freeit.walkingtogether.core.GoogleSignClient
import ru.freeit.walkingtogether.core.GoogleAuthData
import ru.freeit.walkingtogether.presentation.screens.register.RegisterScreen

class MyNavigator(private val manager: FragmentManager) {
    fun register(id: String) {
        manager.beginTransaction()
            .replace(R.id.fragment_container, RegisterScreen.newInstance(id))
            .addToBackStack(null)
            .commit()
    }
}

class IntroScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = IntroScreenBinding.inflate(inflater, container, false)

        val account = GoogleAuthData(requireContext())

        val googleSignClient = GoogleSignClient(requireActivity())
        val navigator = MyNavigator(parentFragmentManager)

        if (account.isLogin()) {
//            parentFragmentManager.beginTransaction()
//                .replace(R.id.fragment_container, RegisterScreen.newInstance(account.id()))
//                .addToBackStack(null)
//                .commit()
            navigator.register(account.id())
        }

        val googleSignInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(activityResult.data)
            try {
                val account = task.getResult(ApiException::class.java)

                navigator.register(account.id!!)
//                parentFragmentManager.beginTransaction()
//                    .replace(R.id.fragment_container, RegisterScreen.newInstance(googleAccountId))
//                    .addToBackStack(null)
//                    .commit()

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