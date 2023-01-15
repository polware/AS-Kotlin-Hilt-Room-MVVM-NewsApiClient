package com.polware.newsapiclient.viewmodel.repositories.datasources

import com.polware.newsapiclient.data.api.NewsApiService
import com.polware.newsapiclient.data.models.APIResponse
import retrofit2.Response

class NewsRemoteDataSourceImpl(private val newsApiService: NewsApiService): NewsRemoteDataSource {

    override suspend fun getNewsTopHeadlines(country: String, page: Int, apiKey: String):
            Response<APIResponse> {
        return newsApiService.getNewsTopHeadlines(country, page, apiKey)
    }

    override suspend fun searchFromEverything(searchQuery: String, apiKey: String):
            Response<APIResponse> {
        return newsApiService.searchFromEverything(searchQuery, apiKey)
    }

}