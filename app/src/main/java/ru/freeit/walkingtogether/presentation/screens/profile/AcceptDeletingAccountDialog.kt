package ru.freeit.walkingtogether.presentation.screens.profile

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import ru.freeit.walkingtogether.R
import ru.freeit.walkingtogether.core.delegates.viewBinding
import ru.freeit.walkingtogether.core.extensions.click
import ru.freeit.walkingtogether.databinding.AcceptDeletingAccountDialogBinding

class AcceptDeletingAccountDialog : DialogFragment(R.layout.accept_deleting_account_dialog) {
    private val binding by viewBinding(AcceptDeletingAccountDialogBinding::bind)

    fun show(manager: FragmentManager) {
        this.show(manager, "accept_deleting_account_dialog")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fm = requireActivity().supportFragmentManager

        val params = dialog?.window?.attributes
        params?.width = FrameLayout.LayoutParams.MATCH_PARENT
        dialog?.window?.attributes = params

        binding.yesButton.click {
            AcceptDeletingAccountDialogListener(fm).result(true)
            dismiss()
        }
        binding.notButton.click {
            AcceptDeletingAccountDialogListener(fm).result(false)
            dismiss()
        }
    }

}