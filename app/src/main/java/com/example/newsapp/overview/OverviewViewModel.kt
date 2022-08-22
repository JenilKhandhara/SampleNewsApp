package com.example.newsapp.overview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.News
import com.example.newsapp.data.NewsApi
import com.example.newsapp.repository.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class OverviewViewModel : ViewModel() {

    enum class NewsApiStatus { LOADING, ERROR, DONE }
    private val _status = MutableStateFlow(NewsApiStatus.LOADING)
    val status: StateFlow<NewsApiStatus> = _status
    private val _news = MutableStateFlow(listOf<News>())
    val news: StateFlow<List<News>> = _news

    private val repository = NewsRepository(NewsApi.retrofitService)

    init {
        getNews()
    }

    private fun getNews() {

        _status.value = NewsApiStatus.LOADING
        viewModelScope.launch {
            repository.getNews()
                .catch {
                    _status.value = NewsApiStatus.ERROR
                    _news.value = listOf()
                }
                .collect{
                    _news.value = NewsApi.retrofitService.getNews().articles
                    _status.value = NewsApiStatus.DONE
                }
        }
    }
}