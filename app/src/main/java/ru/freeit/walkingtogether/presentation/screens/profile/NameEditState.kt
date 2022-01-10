package ru.freeit.walkingtogether.presentation.screens.profile

sealed interface NameEditState {
    object Loading : NameEditState
    object Success : NameEditState
    object NameEmpty : NameEditState
}