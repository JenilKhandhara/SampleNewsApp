package com.example.newsapp.overview

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.*
import com.example.newsapp.data.NewsDatabase.Companion.getDatabase
import com.example.newsapp.redux.ApplicationState
import com.example.newsapp.redux.Store
import com.example.newsapp.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class OverviewViewModel(application: Application) :
    AndroidViewModel(application) {

    val store: Store<ApplicationState> = ApplicationStateModule.providesApplicationStateStore()
    private val database = getDatabase(application)
    private val repository = NewsRepository(database)

    init {
        getNews()
    }

    private fun getNews() {

        viewModelScope.launch(Dispatchers.IO) {
            repository.getNews()
                .catch {
                    store.update { applicationState ->
                        return@update applicationState.copy(
                            news = Resource.error(it.message.toString())
                        )
                    }
                }
                .collect {
                    store.update { applicationState ->
                        return@update applicationState.copy(
                            news = Resource.success(it.data)
                        )
                    }
                }
        }
    }
}