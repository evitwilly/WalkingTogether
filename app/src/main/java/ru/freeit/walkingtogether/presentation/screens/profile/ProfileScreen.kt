package ru.freeit.walkingtogether.presentation.screens.profile

import android.os.Bundle
import android.view.View
import androidx.lifecycle.*
import ru.freeit.walkingtogether.R
import ru.freeit.walkingtogether.core.delegates.viewBinding
import ru.freeit.walkingtogether.core.extensions.click
import ru.freeit.walkingtogether.databinding.ProfileScreenBinding
import ru.freeit.walkingtogether.presentation.base.BaseFragment
import ru.freeit.walkingtogether.presentation.screens.profile.dialogs.AcceptDeletingAccountDialog
import ru.freeit.walkingtogether.presentation.screens.profile.dialogs.AcceptDeletingAccountDialogListener
import ru.freeit.walkingtogether.presentation.screens.profile.ui.LoginState
import ru.freeit.walkingtogether.presentation.dialogs.avatar.AvatarListDialog
import ru.freeit.walkingtogether.presentation.dialogs.avatar.AvatarListDialogListener

class ProfileScreen : BaseFragment(R.layout.profile_screen) {

    private val binding by viewBinding(ProfileScreenBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(this, factories.profile()).get(ProfileViewModel::class.java)

        viewModel.observeUser(viewLifecycleOwner) { user ->
            user.apply {
                img(binding.avatarImage)
                name(binding.nameText)
                bioText(binding.bioText)
            }
        }

        binding.avatarBox.click { AvatarListDialog.newInstance(viewModel.isFemale()).show(fm) }

        AvatarListDialogListener(fm).listen(viewLifecycleOwner, viewModel::selectAvatar)
        AcceptDeletingAccountDialogListener(fm).listen(viewLifecycleOwner, viewModel::removeAccount)

        binding.editButton.click(navigator::nameEdit)

        binding.logoutButton.click(viewModel::logout)
        binding.deleteButton.click { AcceptDeletingAccountDialog().show(fm) }

        viewModel.observe(viewLifecycleOwner) { loginState ->
            if (loginState == LoginState.None) {
                navigator.intro()
            }
        }

        viewModel.init()
    }


}