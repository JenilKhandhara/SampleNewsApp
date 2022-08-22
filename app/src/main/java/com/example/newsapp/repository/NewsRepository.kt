package com.example.newsapp.repository

import androidx.room.withTransaction
import com.example.newsapp.data.*
import kotlinx.coroutines.flow.Flow

class NewsRepository(private val database: NewsDatabase) {
    private val newsDao = database.newsDao()

    fun getNews(): Flow<Resource<List<News>>> = networkBoundResource(

        query = {
            newsDao.getAllNews()
        },

        fetch = {
            NewsApi.retrofitService.getNews()
        },

        saveFetchResult = { NewsList ->
            val news = NewsList.articles.mapIndexed { index, it -> News(index,it.title,it.author,it.description,it.urlToImage,it.url) }
            database.withTransaction {
                newsDao.deleteAllNews()
                newsDao.insertNews(news)
            }
        }
    )
}