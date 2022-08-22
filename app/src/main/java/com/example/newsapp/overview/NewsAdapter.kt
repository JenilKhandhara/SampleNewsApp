package com.example.newsapp.overview

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.DetailActivity
import com.example.newsapp.data.News
import com.example.newsapp.databinding.ItemLayoutBinding
import kotlinx.coroutines.NonDisposableHandle.parent

class NewsAdapter :
    ListAdapter<News,
            NewsAdapter.NewsViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<News>() {
        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem == newItem
        }
    }

    class NewsViewHolder(
        private var binding:
        ItemLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(News: News) {
            binding.news = News
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(ItemLayoutBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = getItem(position)
        holder.bind(news)
        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra("URL", news.url)
            holder.itemView.context.startActivity(intent)
        }
    }

}