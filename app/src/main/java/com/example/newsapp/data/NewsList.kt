package com.example.newsapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

data class NewsList(
    val title: String?,
    val author: String?,
    val description: String?,
    val urlToImage: String?,
    val url: String?
)
