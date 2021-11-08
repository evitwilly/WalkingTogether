package ru.freeit.walkingtogether.presentation.screens.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import ru.freeit.walkingtogether.databinding.AvatarListDialogBinding

class AvatarListDialog : DialogFragment() {

    fun show(manager: FragmentManager) {
        this.show(manager, "avatar_list_dialog")
    }

    companion object {
        fun newInstance(isFemale: Boolean) = AvatarListDialog().apply {
            arguments = bundleOf("is_female_key" to isFemale)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = AvatarListDialogBinding.inflate(inflater)

        val images = if (requireArguments().getBoolean("is_female_key", true))
            AvatarImages().femaleImages()
        else
            AvatarImages().maleImages()

        binding.avatarList.adapter = AvatarAdapter({ drawableId ->
            AvatarListDialogListener(parentFragmentManager).setResult(drawableId)
            dismiss()
        }, images)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val params = dialog?.window?.attributes
        params?.width = FrameLayout.LayoutParams.MATCH_PARENT
        dialog?.window?.attributes = params
    }
}