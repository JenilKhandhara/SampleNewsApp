package com.example.newsapp.repository

import com.example.newsapp.data.NewsApiService
import com.example.newsapp.data.NewsApiState
import com.example.newsapp.data.NewsResponse
import com.example.newsapp.overview.OverviewViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NewsRepository(private val apiService: NewsApiService) {
    suspend fun getNews(): Flow<NewsApiState<NewsResponse>> {
        return flow {
            val news = apiService.getNews()
            emit(NewsApiState.success(news))
        }
    }
}