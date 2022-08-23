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
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
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
            viewModel.store.stateFlow.map { it.news }.distinctUntilChanged().collect {
                val statusImageView = binding.statusImage
                val statusTextView = binding.statusText
                when (it.status) {
                    Resource.Status.LOADING -> {
                        statusTextView.visibility = View.GONE
                        statusImageView.visibility = View.VISIBLE
                        statusImageView.setImageResource(R.drawable.loading_animation)
                    }
                    Resource.Status.ERROR -> {
                        statusImageView.visibility = View.GONE
                        statusTextView.visibility = View.VISIBLE
                        statusTextView.text = getString(R.string.news_error)
                    }
                    Resource.Status.SUCCESS -> {
                        statusImageView.visibility = View.GONE
                        statusTextView.visibility = View.GONE
                        if(it.data.isNullOrEmpty()) {
                            statusTextView.visibility = View.VISIBLE
                             statusTextView.text = getString(R.string.no_internet)
                        }
                        else {
                            adapter.submitList(it.data)
                        }
                    }
                }
            }
        }
    }
}