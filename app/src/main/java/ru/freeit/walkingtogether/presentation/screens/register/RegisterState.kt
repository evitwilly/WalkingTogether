package ru.freeit.walkingtogether.presentation.screens.register

sealed class RegisterState {
    object NameEmpty : RegisterState()
    object BioEmpty : RegisterState()
    object Success : RegisterState()
    object Failure : RegisterState()
    object Loading: RegisterState()
}