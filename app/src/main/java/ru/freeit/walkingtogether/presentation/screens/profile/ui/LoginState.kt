package ru.freeit.walkingtogether.presentation.screens.profile.ui

sealed class LoginState {
    object Logged : LoginState()
    object None : LoginState()
}