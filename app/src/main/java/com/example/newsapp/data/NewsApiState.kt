package com.example.newsapp.data

data class NewsApiState<out T>(val status: Status, val data: T?, val message: String?) {

    companion object {

        fun <T> success(data: T?): NewsApiState<T> {
            return NewsApiState(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String): NewsApiState<T> {
            return NewsApiState(Status.ERROR, null, msg)
        }

        fun <T> loading(): NewsApiState<T> {
            return NewsApiState(Status.LOADING, null, null)
        }
    }

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }
}