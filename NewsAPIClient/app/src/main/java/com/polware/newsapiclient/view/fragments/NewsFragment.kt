package com.polware.newsapiclient.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AbsListView
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.polware.newsapiclient.BuildConfig
import com.polware.newsapiclient.R
import com.polware.newsapiclient.data.utils.Resource
import com.polware.newsapiclient.databinding.FragmentNewsBinding
import com.polware.newsapiclient.view.MainActivity
import com.polware.newsapiclient.view.NewsAdapter
import com.polware.newsapiclient.viewmodel.NewsViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NewsFragment : Fragment() {
    private var _bindingNF: FragmentNewsBinding? = null
    private val bindingNews get() = _bindingNF!!
    private lateinit var viewModelFragment: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    private var country = "us"
    private var page = 1
    private val apiKey = BuildConfig.API_KEY
    private var isScrolling = false
    private var isLoading = false
    private var isLastPage = false
    private var totalPages = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _bindingNF = FragmentNewsBinding.inflate(inflater, container, false)
        return bindingNews.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Obtenemos el ViewModel creado en la Main Activity
        viewModelFragment = (activity as MainActivity).viewModel
        newsAdapter = (activity as MainActivity).newsAdapter

        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("selected_article", it)
            }
            findNavController().navigate(R.id.action_newsFragment_to_detailsNewsFragment, bundle)
        }

        initRecyclerView()
        loadNewsList()
        setSearchView()
    }

    private fun initRecyclerView() {
        bindingNews.recyclerViewNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@NewsFragment.onScrollListener)
        }
    }

    private fun loadNewsList() {
        viewModelFragment.getNewsHeadlines(country, page, apiKey)
        viewModelFragment.newsHeadlines.observe(viewLifecycleOwner) {
            response ->
            when(response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        newsAdapter.differ.submitList(it.articles.toList())
                        totalPages = if (it.totalResults % 20 == 0) {
                            it.totalResults / 20
                            }
                            else {
                                it.totalResults / 20+1
                            }
                        isLastPage = page == totalPages
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        Toast.makeText(activity, "Response error: $response", Toast.LENGTH_SHORT).show()
                        Log.i("Response Error: ", response.message)
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }
    }

    private fun showProgressBar() {
        isLoading = true
        bindingNews.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        isLoading = false
        bindingNews.progressBar.visibility = View.GONE
    }

    // Realiza nueva petición a la API al mostrar los 20 items que contiene cada página
    private val onScrollListener = object : RecyclerView.OnScrollListener() {

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = bindingNews.recyclerViewNews.layoutManager as LinearLayoutManager
            val currentListSize = layoutManager.itemCount
            val visibleItems = layoutManager.childCount
            val topPosition = layoutManager.findFirstVisibleItemPosition()
            val hasReachedToEnd = topPosition+visibleItems >= currentListSize
            val shouldPaginate = !isLoading && !isLastPage && hasReachedToEnd && isScrolling
            if(shouldPaginate){
                page++
                viewModelFragment.getNewsHeadlines(country, page, apiKey)
                isScrolling = false
            }
        }
    }

    private fun setSearchView() {
        bindingNews.searchViewNews.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(inputText: String?): Boolean {
                if (inputText!!.isNotEmpty()) {
                    viewModelFragment.searchNews(inputText.toString(), apiKey)
                    loadSearchedNews()
                }
                return false
            }

            override fun onQueryTextChange(inputText: String?): Boolean {
                if (inputText!!.isNotEmpty()) {
                    MainScope().launch {
                        delay(1500)
                        viewModelFragment.searchNews(inputText.toString(), apiKey)
                        loadSearchedNews()
                    }
                }
                return false
            }
        })

        // Reset the list News when user clicks on close button
        val mSearchView: SearchView = bindingNews.searchViewNews
        val closeBtnId = mSearchView.context.resources
            .getIdentifier("android:id/search_close_btn", null, null)
        val closeBtn = mSearchView.findViewById<ImageView>(closeBtnId)
        closeBtn?.setOnClickListener {
            mSearchView.setQuery("", false)
            mSearchView.isIconified = true // Replace the x icon with the search icon
            mSearchView.clearFocus()
            initRecyclerView()
            loadNewsList()
        }
    }

    fun loadSearchedNews() {
        // Auto hide the keyboard of screen
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        viewModelFragment.searchedNews.observe(viewLifecycleOwner) {
                response ->
            when(response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        newsAdapter.differ.submitList(it.articles.toList())
                        totalPages = if (it.totalResults % 20 == 0) {
                            it.totalResults / 20
                        }
                        else {
                            it.totalResults / 20+1
                        }
                        isLastPage = page == totalPages
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        Toast.makeText(activity, "Load search error: ${response.message}", Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }
    }

}