package com.polware.newsapiclient.viewmodel.repositories.datasources

import com.polware.newsapiclient.data.models.APIResponse
import retrofit2.Response

interface NewsRemoteDataSource {

    suspend fun getNewsTopHeadlines(country: String, page: Int, apiKey: String): Response<APIResponse>

    suspend fun searchFromEverything(searchQuery: String, apiKey:String): Response<APIResponse>

}