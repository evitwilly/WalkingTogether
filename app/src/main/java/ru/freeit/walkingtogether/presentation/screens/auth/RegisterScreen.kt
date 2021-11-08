package ru.freeit.walkingtogether.presentation.screens.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.freeit.walkingtogether.databinding.RegisterScreenBinding

class RegisterScreen : Fragment() {

    private fun AppCompatRadioButton.runIfChecked(onChecked: () -> Unit) {
        if (isChecked) onChecked()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = RegisterScreenBinding.inflate(inflater)

        val id = requireArguments().getString(googleAccountIdKey) ?: throw IllegalStateException("google id is empty!")
        val viewModel = ViewModelProvider(this, RegisterViewModelFactory(id, this, savedInstanceState)).get(RegisterViewModel::class.java)

        binding.avatarImage.setOnClickListener {
            AvatarListDialog.newInstance(binding.femaleCheckbox.isChecked)
                .show(parentFragmentManager)
        }

        AvatarListDialogListener(parentFragmentManager).listen(viewLifecycleOwner, viewModel::selectAvatar)
        viewModel.observeAvatar(viewLifecycleOwner, binding.avatarImage::setImageResource)

        binding.femaleCheckbox.setOnClickListener {
            binding.femaleCheckbox.runIfChecked { viewModel.checkFemale() }
        }
        binding.maleCheckbox.setOnClickListener {
            binding.maleCheckbox.runIfChecked { viewModel.checkMale() }
        }
        binding.registerButton.setOnClickListener {
            viewModel.register(binding.femaleCheckbox.isChecked, binding.nameEdit.text.toString(), binding.bioEdit.text.toString())
        }

        viewModel.init()



        return binding.root
    }

    companion object {
        private const val googleAccountIdKey = "google_account_id_key"
        fun newInstance(id: String) = RegisterScreen().apply {
            arguments = bundleOf(googleAccountIdKey to id)
        }
    }

}