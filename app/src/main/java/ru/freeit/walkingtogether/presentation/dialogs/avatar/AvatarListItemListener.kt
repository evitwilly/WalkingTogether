package ru.freeit.walkingtogether.presentation.dialogs.avatar

import ru.freeit.walkingtogether.presentation.base.AvatarImage

fun interface AvatarListItemListener {
    fun onAvatar(avatar: AvatarImage)
}