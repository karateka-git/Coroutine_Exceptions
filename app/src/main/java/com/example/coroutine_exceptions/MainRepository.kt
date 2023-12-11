package com.example.coroutine_exceptions

import kotlinx.coroutines.delay

class MainRepository {
    suspend fun getException(): String {
        delay(1000)
        throw Exception("Data is Exception")
    }
    suspend fun getData(): String {
        delay(1000)
        return "Data is Success"
    }
}