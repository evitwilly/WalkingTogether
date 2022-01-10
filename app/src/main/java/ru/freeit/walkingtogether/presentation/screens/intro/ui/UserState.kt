package ru.freeit.walkingtogether.presentation.screens.intro.ui

sealed class UserState {
    object Success : UserState()
    data class None(private val id: String) : UserState() {
        fun id() = id
    }
}