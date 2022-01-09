package ru.freeit.walkingtogether.presentation.screens.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import ru.freeit.walkingtogether.R
import ru.freeit.walkingtogether.core.App
import ru.freeit.walkingtogether.core.delegates.viewBinding
import ru.freeit.walkingtogether.core.extensions.runIfChecked
import ru.freeit.walkingtogether.databinding.IntroScreenBinding
import ru.freeit.walkingtogether.databinding.RegisterScreenBinding
import ru.freeit.walkingtogether.presentation.disable
import ru.freeit.walkingtogether.presentation.enable
import ru.freeit.walkingtogether.presentation.screens.intro.BaseFragment
import ru.freeit.walkingtogether.presentation.screens.intro.MyNavigator

class RegisterScreen : BaseFragment(R.layout.register_screen) {

    private val binding by viewBinding(RegisterScreenBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getString(googleAccountIdKey) ?: throw IllegalStateException("google id is empty!")

        val viewModel = ViewModelProvider(this, factories.register(id, this, savedInstanceState))
            .get(RegisterViewModel::class.java)

        binding.avatarImage.setOnClickListener {
            AvatarListDialog.newInstance(binding.femaleCheckbox.isChecked)
                .show(parentFragmentManager)
        }

        AvatarListDialogListener(parentFragmentManager).listen(viewLifecycleOwner, viewModel::selectAvatar)

        viewModel.observeAvatar(viewLifecycleOwner) { avatarImage ->
            binding.avatarImage.setImageResource(avatarImage.drawable())
        }

        viewModel.observeRegisterState(viewLifecycleOwner) { registerState ->
            binding.nameBox.error = null
            binding.bioBox.error = null
            binding.progress.isVisible = false
            when (registerState) {
                RegisterState.NameEmpty -> {
                    binding.nameBox.error = getString(R.string.name_is_emtpy)
                }
                RegisterState.Loading -> {
                    binding.progress.isVisible = true
                }
                RegisterState.BioEmpty -> {
                    binding.bioBox.error = getString(R.string.bio_is_empty)
                }
                RegisterState.Success -> { navigator.map() }
                RegisterState.Failure -> {
                    Snackbar.make(binding.root, getString(R.string.missing_internet), Snackbar.LENGTH_SHORT)
                        .show()
                }
            }
            binding.registerButton.enable()
        }

        binding.backButton?.setOnClickListener { navigator.back() }

        binding.femaleCheckbox.setOnClickListener {
            binding.femaleCheckbox.runIfChecked { viewModel.checkFemale() }
        }
        binding.maleCheckbox.setOnClickListener {
            binding.maleCheckbox.runIfChecked { viewModel.checkMale() }
        }

        binding.registerButton.setOnClickListener {
            binding.registerButton.disable()
            viewModel.register(binding.femaleCheckbox.isChecked, binding.nameEdit.text.toString(),
                binding.bioEdit.text.toString())
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