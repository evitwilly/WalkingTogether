package ru.freeit.walkingtogether.presentation.screens.name.ui

sealed interface NameEditState {
    object Loading : NameEditState
    object Success : NameEditState
    object NameEmpty : NameEditState
}