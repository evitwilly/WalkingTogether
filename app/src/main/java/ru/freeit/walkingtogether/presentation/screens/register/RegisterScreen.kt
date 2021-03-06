package ru.freeit.walkingtogether.presentation.screens.register

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import ru.freeit.walkingtogether.R
import ru.freeit.walkingtogether.core.delegates.viewBinding
import ru.freeit.walkingtogether.core.extensions.*
import ru.freeit.walkingtogether.databinding.RegisterScreenBinding
import ru.freeit.walkingtogether.presentation.base.BaseFragment
import ru.freeit.walkingtogether.presentation.screens.register.anim.RegisterViewAnimating
import ru.freeit.walkingtogether.presentation.dialogs.avatar.AvatarListDialog
import ru.freeit.walkingtogether.presentation.dialogs.avatar.AvatarListDialogListener
import ru.freeit.walkingtogether.presentation.screens.register.ui.RegisterOptionsUi
import ru.freeit.walkingtogether.presentation.screens.register.ui.RegisterState

class RegisterScreen : BaseFragment(R.layout.register_screen) {

    private val binding by viewBinding(RegisterScreenBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(this, factories.register(arguments?.getString(googleAccountIdKey).emptyStringIfNull(), this, savedInstanceState))
            .get(RegisterViewModel::class.java)

        binding.avatarImage.setOnClickListener {
            AvatarListDialog.newInstance(binding.femaleCheckbox.isChecked).show(parentFragmentManager)
        }

        AvatarListDialogListener(parentFragmentManager).listen(viewLifecycleOwner, viewModel::selectAvatar)

        viewModel.observeAvatar(viewLifecycleOwner) { avatarImage -> avatarImage.img(binding.avatarImage) }
        viewModel.observeRegisterState(viewLifecycleOwner) { registerState ->
            binding.nameBox.resetError()
            binding.bioBox.resetError()
            binding.progress.gone()
            when (registerState) {
                RegisterState.NameEmpty -> binding.nameBox.error(R.string.name_is_emtpy)
                RegisterState.BioEmpty -> binding.bioBox.error(R.string.bio_is_empty)
                RegisterState.Loading -> binding.progress.visible()
                RegisterState.Success -> navigator.main()
                RegisterState.Failure -> binding.root.snackBar(R.string.missing_internet)
                RegisterState.GoogleError -> {
                    binding.avatarBox.gone()
                    binding.gender.gone()
                    binding.nameBox.gone()
                    binding.bioBox.gone()
                    binding.registerButton.gone()
                    binding.progress.gone()
                    binding.googleErrorBox.root.visible()
                }
            }
            binding.registerButton.enable()
        }



        val registerViewAnimating = RegisterViewAnimating(binding.avatarBox, binding.gender, binding.backButton!!)
        onKeyboardVisibility(registerViewAnimating::animate)

        binding.backButton?.click(navigator::back)
        binding.femaleCheckbox.click { binding.femaleCheckbox.runIfChecked(viewModel::checkFemale) }
        binding.maleCheckbox.click { binding.maleCheckbox.runIfChecked(viewModel::checkMale) }

        binding.registerButton.setOnClickListener {
            binding.registerButton.disable()
            viewModel.register(
                RegisterOptionsUi(binding.femaleCheckbox.isChecked,
                binding.nameEdit.str(), binding.bioEdit.str())
            )
        }

        viewModel.init()
    }

    companion object {

        private const val googleAccountIdKey = "google_account_id_key"

        fun newInstance(id: String) = RegisterScreen().apply {
            arguments = bundleOf(googleAccountIdKey to id)
        }
    }

}