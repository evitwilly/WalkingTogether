package ru.freeit.walkingtogether.presentation.screens.intro

sealed class UserState {
    object Success : UserState()
    data class None(private val id: String) : UserState() {
        fun id() = id
    }
}