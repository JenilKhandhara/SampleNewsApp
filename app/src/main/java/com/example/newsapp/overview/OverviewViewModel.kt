package com.example.newsapp.overview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.News
import com.example.newsapp.data.NewsApi
import com.example.newsapp.data.NewsApiState
import com.example.newsapp.repository.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class OverviewViewModel : ViewModel() {

    private val _newsState = MutableStateFlow(NewsApiState(NewsApiState.Status.LOADING,listOf<News>(),""))
    val newsState:  StateFlow<NewsApiState<List<News>>> = _newsState

    private val repository = NewsRepository(NewsApi.retrofitService)

    init {
        getNews()
    }

    private fun getNews() {

        _newsState.value = NewsApiState.loading()
        viewModelScope.launch {
            repository.getNews()
                .catch {
                    _newsState.value = NewsApiState.error(it.message.toString())
                }
                .collect{
                    _newsState.value = NewsApiState.success(it.data!!.articles)
                }
        }
    }
}