package com.example.newsapp.repository

import com.example.newsapp.data.NewsApiService
import com.example.newsapp.data.NewsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NewsRepository(private val apiService: NewsApiService) {
    suspend fun getNews(): Flow<NewsResponse> {
        return flow {
            val news = apiService.getNews()
            emit(news)
        }
    }
}