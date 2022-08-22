package com.example.newsapp.overview

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.newsapp.R
import com.example.newsapp.data.Resource
import com.example.newsapp.databinding.FragmentOverviewBinding
import kotlinx.coroutines.launch

class OverviewFragment : Fragment() {

    private val viewModel: OverviewViewModel by viewModels()
    private lateinit var binding: FragmentOverviewBinding
    private val adapter: NewsAdapter = NewsAdapter()

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
            viewModel.newsState.collect {
                val statusImageView = binding.statusImage
                Log.d("data","$it")
                when (it.status) {
                    Resource.Status.LOADING -> {
                        statusImageView.visibility = View.VISIBLE
                        statusImageView.setImageResource(R.drawable.loading_animation)
                    }
                    Resource.Status.ERROR -> {
                        statusImageView.visibility = View.VISIBLE
                        statusImageView.setImageResource(R.drawable.ic_connection_error)
                    }
                    Resource.Status.SUCCESS -> {
                        statusImageView.visibility = View.GONE
                        adapter.submitList(it.data)
                    }
                }
            }
        }
    }
}