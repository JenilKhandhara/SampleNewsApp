package com.example.newsapp.overview

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.newsapp.DetailActivity
import com.example.newsapp.R
import com.example.newsapp.data.News
import com.example.newsapp.databinding.ItemLayoutBinding

class NewsAdapter :
    ListAdapter<News,
            NewsAdapter.NewsViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<News>() {
        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem == newItem
        }
    }

    class NewsViewHolder(
        private var binding:
        ItemLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        var newsImage = binding.image
        var newsTitle = binding.title
        var newsDescription = binding.description
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(ItemLayoutBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = getItem(position)
        holder.newsTitle.text = news.title
        holder.newsDescription.text = news.description
        news.urlToImage?.let {
            val imgUri = news.urlToImage.toUri().buildUpon().scheme("https").build()
            holder.newsImage.load(imgUri) {
                placeholder(R.drawable.loading_animation)
                error(R.drawable.ic_broken_image)
            }
        }
        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra("URL", news.url)
            holder.itemView.context.startActivity(intent)
        }
    }

}