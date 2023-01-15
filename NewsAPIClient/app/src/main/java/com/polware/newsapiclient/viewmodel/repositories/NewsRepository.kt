package com.polware.newsapiclient.viewmodel.repositories

import com.polware.newsapiclient.data.models.APIResponse
import com.polware.newsapiclient.data.models.Article
import com.polware.newsapiclient.data.utils.Resource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    suspend fun getNewsHeadlines(country: String, page: Int, apiKey: String): Resource<APIResponse>

    suspend fun searchNews(searchQuery: String, apiKey: String): Resource<APIResponse>

    fun getSavedNews(): Flow<List<Article>>

    suspend fun saveNews(article: Article)

    suspend fun deleteNews(article: Article)

}