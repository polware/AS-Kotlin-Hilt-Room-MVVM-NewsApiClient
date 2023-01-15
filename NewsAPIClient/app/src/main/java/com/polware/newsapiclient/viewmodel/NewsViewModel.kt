package com.polware.newsapiclient.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.polware.newsapiclient.data.models.APIResponse
import com.polware.newsapiclient.data.models.Article
import com.polware.newsapiclient.data.utils.Resource
import com.polware.newsapiclient.viewmodel.usecases.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NewsViewModel(private val app: Application, private val getNewsHeadlinesUseCase:
        GetNewsHeadlinesUseCase, private val searchNewsUseCase: SearchNewsUseCase,
                    private val saveNewsUseCase: SaveNewsUseCase,
                    private val getSavedNewsUseCase: GetSavedNewsUseCase,
                    private val deleteSavedNewsUseCase: DeleteSavedNewsUseCase): AndroidViewModel(app) {

    val newsHeadlines: MutableLiveData<Resource<APIResponse>> = MutableLiveData()
    val searchedNews: MutableLiveData<Resource<APIResponse>> = MutableLiveData()

    fun getNewsHeadlines(country: String, page: Int, apiKey: String) =
        viewModelScope.launch(Dispatchers.IO) {
            newsHeadlines.postValue(Resource.Loading())
            try {
                val apiResult = getNewsHeadlinesUseCase.execute(country, page,apiKey)
                if (isNetworkAvailable(app)) {
                    //val apiResult = getNewsHeadlinesUseCase.execute(country, page,apiKey)
                    newsHeadlines.postValue(apiResult)
                }
                else {
                    newsHeadlines.postValue(Resource.Error("Internet connection unavailable"))
                }
            }
            catch (e: Exception) {
                newsHeadlines.postValue(Resource.Error(e.message.toString()))
            }
    }

    fun searchNews(searchQuery: String, apiKey: String) = viewModelScope.launch {
        searchedNews.postValue(Resource.Loading())
        try {
            if (isNetworkAvailable(app)) {
                val response = searchNewsUseCase.execute(searchQuery, apiKey)
                searchedNews.postValue(response)
            }
            else {
                searchedNews.postValue(Resource.Error("Internet connection unavailable"))
            }
        }
        catch (e: Exception) {
            searchedNews.postValue(Resource.Error(e.message.toString()))
        }
    }

    private fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }

    fun saveArticle(article: Article) = viewModelScope.launch {
        saveNewsUseCase.execute(article)
    }

    fun getSavedNews() = liveData {
        getSavedNewsUseCase.execute().collect {
            emit(it)
        }
    }

    fun deleteArticle(article: Article) = viewModelScope.launch {
        deleteSavedNewsUseCase.execute(article)
    }

}