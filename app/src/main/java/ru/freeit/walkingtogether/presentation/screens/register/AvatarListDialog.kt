package ru.freeit.walkingtogether.presentation.screens.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import ru.freeit.walkingtogether.R
import ru.freeit.walkingtogether.core.App
import ru.freeit.walkingtogether.core.delegates.viewBinding
import ru.freeit.walkingtogether.databinding.AvatarListDialogBinding

class AvatarListDialog : DialogFragment(R.layout.avatar_list_dialog) {

    private val binding by viewBinding(AvatarListDialogBinding::bind)

    fun show(manager: FragmentManager) {
        this.show(manager, "avatar_list_dialog")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val avatarImages = (requireActivity().application as App).images

        val images = if (requireArguments().getBoolean(isFemaleKey, true)) avatarImages.female() else avatarImages.male()

        binding.avatarList.adapter = AvatarAdapter(images) { avatar ->
            AvatarListDialogListener(parentFragmentManager).setResult(avatar.id())
            dismiss()
        }
    }

    override fun onResume() {
        super.onResume()
        val params = dialog?.window?.attributes
        params?.width = FrameLayout.LayoutParams.MATCH_PARENT
        dialog?.window?.attributes = params
    }

    companion object {
        private const val isFemaleKey = "is_female_key"

        fun newInstance(isFemale: Boolean) = AvatarListDialog().apply {
            arguments = bundleOf(isFemaleKey to isFemale)
        }
    }

}