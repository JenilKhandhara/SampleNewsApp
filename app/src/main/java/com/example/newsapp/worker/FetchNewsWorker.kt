package com.example.newsapp.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.newsapp.data.NewsDatabase.Companion.getDatabase
import com.example.newsapp.repository.NewsRepository
import retrofit2.HttpException


class FetchNewsWorker (context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    companion object {
        const val WORK_NAME = "com.example.android.work.FetchNewsWorker"
    }
    override suspend fun doWork(): Result {
        val database = getDatabase(applicationContext)
        val repository = NewsRepository(database)
        try {
            repository.refreshNews()
            Log.d("WorkManager", "Refreshing data")
        }
        catch (e: HttpException) {
            return Result.retry()
        }
        return  Result.success()
    }
}