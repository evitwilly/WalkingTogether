package ru.freeit.walkingtogether.presentation.screens.register

import android.widget.ImageView
import ru.freeit.walkingtogether.core.extensions.setImageResourceWithAnimating

data class AvatarImage(
    private val id: Int,
    private val drawableId: Int,
) {
    fun id() = id
    fun img(view: ImageView) {
        view.setImageResourceWithAnimating(drawableId)
    }
}