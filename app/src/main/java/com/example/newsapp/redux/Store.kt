package com.example.newsapp.redux

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class Store<T>(initialState: T) {
    val stateFlow: MutableStateFlow<T>
    val reducer: Reducer<T>
    init {
        stateFlow = MutableStateFlow(initialState)
        reducer = Reducer(stateFlow)
    }
}