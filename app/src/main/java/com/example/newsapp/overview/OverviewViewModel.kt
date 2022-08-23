package com.example.newsapp.overview

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.News
import com.example.newsapp.data.NewsApi
import com.example.newsapp.data.Resource
import com.example.newsapp.data.NewsDatabase.Companion.getDatabase
import com.example.newsapp.data.NewsList
import com.example.newsapp.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class OverviewViewModel(application: Application) : AndroidViewModel(application) {

    private val _newsState = MutableStateFlow(Resource(Resource.Status.LOADING,listOf<News>(),""))
    val newsState:  StateFlow<Resource<List<News>>> = _newsState
    private val database = getDatabase(application)
    private val repository = NewsRepository(database)
    init {
        getNews()
    }

    private fun getNews() {

        _newsState.value = Resource.loading()
        viewModelScope.launch(Dispatchers.IO) {
            repository.getNews()
                .catch {
                    _newsState.value = Resource.error(it.message.toString())
                }
                .collect{
                    _newsState.value = Resource.success(it.data)
                }
        }
    }
}