package com.example.newsapp

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.newsapp.data.News
import com.example.newsapp.overview.NewsAdapter
import com.example.newsapp.overview.OverviewViewModel

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView,
                     data: List<News>?) {
    val adapter = recyclerView.adapter as NewsAdapter
    adapter.submitList(data)
}

@BindingAdapter("newsApiStatus")
fun bindStatus(
    statusImageView: ImageView,
    status: OverviewViewModel.NewsApiStatus?) {
    when (status) {
        OverviewViewModel.NewsApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        OverviewViewModel.NewsApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        OverviewViewModel.NewsApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}
