package com.example.newsapp.repository

import androidx.room.withTransaction
import com.example.newsapp.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class NewsRepository(private val database: NewsDatabase) {
    private val newsDao = database.newsDao()

    fun getNews(): Flow<Resource<List<News>>> = newsDao.getAllNews().map { Resource.success(it) }
    suspend fun refreshNews() {
        withContext(Dispatchers.IO) {
            val newsList = NewsApi.retrofitService.getNews()
            val news = newsList.articles.mapIndexed { index, it -> News(index,it.title,it.author,it.description,it.urlToImage,it.url) }
            database.withTransaction {
                newsDao.deleteAllNews()
                newsDao.insertNews(news)
            }
        }
    }
}