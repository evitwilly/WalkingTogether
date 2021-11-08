package ru.freeit.walkingtogether.presentation.screens.auth

import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner

class AvatarListDialogListener(private val manager: FragmentManager) {

    private val requestKey = "avatar_list_dialog_key"
    private val drawableResourceKey = "drawable_resource_key"

    fun setResult(imgId: Int) {
        manager.setFragmentResult(requestKey, bundleOf(drawableResourceKey to imgId))
    }

    fun listen(lifecycleOwner: LifecycleOwner, onDrawableResourceListener: (imgId: Int) -> Unit) {
        manager.setFragmentResultListener(requestKey, lifecycleOwner, { _, result ->
            onDrawableResourceListener(result.getInt(drawableResourceKey, 0))
        })
    }
}