package ru.freeit.walkingtogether.presentation.screens.profile

import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner

class AcceptDeletingAccountDialogListener(private val manager: FragmentManager) {

    private val requestKey = "accept_deleting_account_dialog_request_key"
    private val valueKey = "accept_deleting_account_dialog_value_key"

    fun result(isYes: Boolean) {
        manager.setFragmentResult(requestKey, bundleOf(valueKey to isYes))
    }

    fun listen(lifecycleOwner: LifecycleOwner, onValue: (isYes: Boolean) -> Unit) {
        manager.setFragmentResultListener(requestKey, lifecycleOwner, { _, result ->
            onValue(result.getBoolean(valueKey, false))
        })
    }
}