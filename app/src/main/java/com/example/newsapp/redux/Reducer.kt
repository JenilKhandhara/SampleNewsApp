package com.example.newsapp.redux

import kotlinx.coroutines.flow.MutableStateFlow

class Reducer<T>(private val stateFlow: MutableStateFlow<T>) {

    fun update(updateBlock: (T) -> T) {
        val newState = updateBlock(stateFlow.value)
        stateFlow.value = newState
    }
}