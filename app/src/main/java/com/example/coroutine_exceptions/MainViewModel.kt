package com.example.coroutine_exceptions

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class MainViewModel : ViewModel() {
    private val mainRepository = MainRepository()
    val list: MutableLiveData<List<String>> = MutableLiveData(listOf())

    private val ceh = CoroutineExceptionHandler { coroutineContext, throwable ->
        list.setValue(list.value?.plus(throwable.toString()))
    }

    fun getSimpleException() {
        viewModelScope.launch(ceh) {
            supervisorScope {
                launch {
                    mainRepository.getException().also {
                        list.setValue(list.value?.plus(it))
                    }
                }
                launch {
                    mainRepository.getData().also {
                        list.setValue(list.value?.plus(it))
                    }
                }
            }
        }
    }
}