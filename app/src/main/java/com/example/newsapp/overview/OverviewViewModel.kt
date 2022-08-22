package com.example.newsapp.overview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.News
import com.example.newsapp.data.NewsApi
import kotlinx.coroutines.launch

class OverviewViewModel : ViewModel() {

    enum class NewsApiStatus { LOADING, ERROR, DONE }
    private val _status = MutableLiveData<NewsApiStatus>()
    val status: LiveData<NewsApiStatus> = _status
    private val _news = MutableLiveData<List<News>>()
    val news: LiveData<List<News>> = _news

    init {
        getNews()
    }

    private fun getNews() {
        viewModelScope.launch {
            _status.value = NewsApiStatus.LOADING
            try {
                _news.value = NewsApi.retrofitService.getNews().articles
                _status.value = NewsApiStatus.DONE
            } catch (e: Exception) {
                _status.value = NewsApiStatus.ERROR
                _news.value = listOf()
            }
        }
    }
}