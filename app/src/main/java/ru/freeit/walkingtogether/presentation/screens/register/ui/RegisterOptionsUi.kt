package ru.freeit.walkingtogether.presentation.screens.register.ui

import ru.freeit.walkingtogether.data.firebasedb.entity.FirebaseUser

class RegisterOptionsUi(
    private val isFemale: Boolean,
    private val name: String,
    private val bio: String
) {

    fun emptyName() = name.isBlank()
    fun emptyBio() = bio.isBlank()
    fun firebaseUser(id: String, avatarId: Int) = FirebaseUser(id, name, bio, isFemale, avatarId)
}