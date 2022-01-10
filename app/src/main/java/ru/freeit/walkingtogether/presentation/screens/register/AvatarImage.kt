package ru.freeit.walkingtogether.presentation.screens.register

import android.widget.ImageView

data class AvatarImage(
    private val id: Int,
    private val drawableId: Int,
) {
    fun id() = id
    fun img(view: ImageView) {
        view.setImageResource(drawableId)
    }
}