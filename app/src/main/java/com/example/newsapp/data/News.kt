package com.example.newsapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity()
data class News(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val title: String?,
    val author: String?,
    val description: String?,
    val urlToImage: String?,
    val url: String?
)
