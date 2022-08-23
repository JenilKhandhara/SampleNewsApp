package com.example.newsapp.redux

import com.example.newsapp.data.News
import com.example.newsapp.data.Resource

data class ApplicationState(
    val news: Resource<List<News>> = Resource(Resource.Status.LOADING,listOf(),"")
)
