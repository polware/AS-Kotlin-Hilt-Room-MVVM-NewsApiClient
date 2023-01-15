package com.polware.newsapiclient.view.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.polware.newsapiclient.R
import com.polware.newsapiclient.databinding.FragmentDetailsNewsBinding
import com.polware.newsapiclient.view.MainActivity
import com.polware.newsapiclient.viewmodel.NewsViewModel

class DetailsNewsFragment : Fragment() {
    private var _bindingDN: FragmentDetailsNewsBinding? = null
    val bindingDetails get() = _bindingDN!!
    private lateinit var viewModelFragment: NewsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        _bindingDN = FragmentDetailsNewsBinding.inflate(inflater, container, false)
        return bindingDetails.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _bindingDN = FragmentDetailsNewsBinding.bind(view)

        viewModelFragment = (activity as MainActivity).viewModel
        val args: DetailsNewsFragmentArgs by navArgs()
        val article = args.selectedArticle

        bindingDetails.webViewNews.apply {
            webViewClient = MyWebViewClient()
            if (article.url != null) {
                loadUrl(article.url)
            }
        }

        bindingDetails.fabSaveNews.setOnClickListener {
            viewModelFragment.saveArticle(article)
            bindingDetails.fabSaveNews.visibility = View.GONE
            Snackbar.make(view, "Article saved!", Snackbar.LENGTH_SHORT).show()
        }
    }

    inner class MyWebViewClient: WebViewClient() {
        private val loadProgress = bindingDetails.progressBarDetailsNews
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            bindingDetails.fabSaveNews.visibility = View.GONE
        }

        override fun onPageFinished(view: WebView, url: String?) {
            super.onPageFinished(view, url)
            loadProgress.visibility = View.GONE
            bindingDetails.webViewNews.visibility = View.VISIBLE
            // Finds the previous fragment
            val previousFragment = findNavController().previousBackStackEntry?.destination?.id
            if (previousFragment == R.id.newsFragment) {
                bindingDetails.fabSaveNews.visibility = View.VISIBLE
            }
        }
    }

}