package ru.freeit.walkingtogether.core.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

open class CoroutineViewModel : ViewModel() {

    private val job = Job()

    protected val viewModelScope = CoroutineScope(Dispatchers.Main + job)

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}