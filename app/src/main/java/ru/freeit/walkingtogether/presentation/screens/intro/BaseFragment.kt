package ru.freeit.walkingtogether.presentation.screens.intro

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import ru.freeit.walkingtogether.core.App

abstract class BaseFragment(@LayoutRes layoutRes: Int) : Fragment(layoutRes) {
    protected val factories by lazy {
        (requireActivity().application as App).viewModelFactories
    }
}