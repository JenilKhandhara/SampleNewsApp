package com.example.newsapp.data

import com.example.newsapp.redux.ApplicationState
import com.example.newsapp.redux.Store

object ApplicationStateModule {
    @Volatile
    private var INSTANCE: Store<ApplicationState>? = null
    fun providesApplicationStateStore(): Store<ApplicationState> {
        if (INSTANCE == null) {
            synchronized(this) {
                if (INSTANCE == null) {
                    INSTANCE = Store(ApplicationState())
                }
            }
        }
        return INSTANCE!!
    }
}