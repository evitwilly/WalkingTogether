package ru.freeit.walkingtogether.presentation.dialogs.avatar

import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner

class AvatarListDialogListener(private val manager: FragmentManager) {

    private val requestKey = "avatar_list_dialog_key"
    private val avatarIdKey = "avatar_id_key"

    fun setResult(avatarId: Int) {
        manager.setFragmentResult(requestKey, bundleOf(avatarIdKey to avatarId))
    }

    fun listen(lifecycleOwner: LifecycleOwner, onAvatarId: (imgId: Int) -> Unit) {
        manager.setFragmentResultListener(requestKey, lifecycleOwner, { _, result ->
            onAvatarId(result.getInt(avatarIdKey, 0))
        })
    }
}