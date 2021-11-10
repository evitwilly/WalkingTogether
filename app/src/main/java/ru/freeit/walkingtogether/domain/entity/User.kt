package ru.freeit.walkingtogether.domain.entity

import ru.freeit.walkingtogether.presentation.screens.register.AvatarImage

data class User(
    private val id: String,
    private val name: String = "",
    private val bio: String = "",
    private val isFemale: Boolean = true,
    private val avatar: AvatarImage
) {

}