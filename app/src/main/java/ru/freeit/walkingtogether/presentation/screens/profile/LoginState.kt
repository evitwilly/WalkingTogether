package ru.freeit.walkingtogether.presentation.screens.profile

sealed class LoginState {
    object Logged : LoginState()
    object None : LoginState()
}