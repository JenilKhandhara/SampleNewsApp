package com.example.newsapp.overview

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentOverviewBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class OverviewFragment : Fragment() {

    private val viewModel: OverviewViewModel by viewModels()
    private lateinit var binding: FragmentOverviewBinding
    private  val adapter: NewsAdapter = NewsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOverviewBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        binding.newsList.adapter = adapter

        newsConsumer()

        return binding.root
    }

    private fun newsConsumer() {
        lifecycleScope.launch {
            viewModel.status.collect{
                val statusImageView = binding.statusImage
                when (it) {
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
        }
        lifecycleScope.launch {
            viewModel.news.collect{
                adapter.submitList(it)
            }
        }
    }

}