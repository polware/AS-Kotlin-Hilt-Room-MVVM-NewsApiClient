package com.polware.newsapiclient.data.api

import com.polware.newsapiclient.data.models.APIResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("top-headlines")
    suspend fun getNewsTopHeadlines(@Query("country") country: String, @Query("page") page: Int,
                                    @Query("apiKey") apiKey: String): Response<APIResponse>

    @GET("everything")
    suspend fun searchFromEverything(@Query("q") searchQuery: String, @Query("apiKey") apiKey: String,
                                     @Query("sortBy") sortBy: String = "popularity",
                                     @Query("language") lang: String = "es",
                                     @Query("pageSize") pageSize: Int = 20): Response<APIResponse>

}