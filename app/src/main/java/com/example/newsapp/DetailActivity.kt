package com.example.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import com.example.newsapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val url:String? = intent.getStringExtra("URL")
        if (url != null) {
            binding.detailWebView.settings.javaScriptEnabled = true
            binding.detailWebView.settings.userAgentString = System.getProperty("http.agent")
            binding.detailWebView.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    binding.progressBar.visibility = View.GONE
                    binding.detailWebView.visibility = View.VISIBLE
                }
            }
            binding.detailWebView.loadUrl(url)
        }
    }
}