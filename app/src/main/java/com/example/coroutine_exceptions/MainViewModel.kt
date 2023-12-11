package com.example.coroutine_exceptions

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.util.concurrent.CancellationException

class MainViewModel : ViewModel() {
    private val mainRepository = MainRepository()
    val list: MutableLiveData<List<String>> = MutableLiveData(listOf())

    fun getSimpleException() {
        val job = viewModelScope.launch {
            try {
                mainRepository.getException().also {
                    list.setValue(list.value?.plus(it))
                }
            } catch (e: Throwable) {
                if (e is CancellationException) {
                    throw e
                } else {
                    list.setValue(list.value?.plus(e.toString()))
                }
            }
            try {
                mainRepository.getException().also {
                    list.setValue(list.value?.plus(it))
                }
            } catch (e: Throwable) {
                list.setValue(list.value?.plus(e.toString()))
            }
        }
        viewModelScope.launch {
            job.cancel(CancellationException("Galya, otmena!"))
        }
    }
}