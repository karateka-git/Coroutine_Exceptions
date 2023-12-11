package com.example.coroutine_exceptions

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val mainRepository = MainRepository()
    val list: MutableLiveData<List<String>> = MutableLiveData(listOf())

    fun getSimpleException() {
        viewModelScope.launch {
            try {
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
            } catch (e: Throwable) {
                list.setValue(list.value?.plus(e.toString()))
            }
        }
    }
}