package ru.freeit.walkingtogether.presentation.screens.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.freeit.walkingtogether.core.App
import ru.freeit.walkingtogether.databinding.ProfileScreenBinding
import ru.freeit.walkingtogether.presentation.screens.intro.MyNavigator
import ru.freeit.walkingtogether.presentation.screens.register.AvatarListDialog
import ru.freeit.walkingtogether.presentation.screens.register.AvatarListDialogListener

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