package com.polware.newsapiclient.viewmodel.usecases

import com.polware.newsapiclient.data.models.APIResponse
import com.polware.newsapiclient.data.utils.Resource
import com.polware.newsapiclient.viewmodel.repositories.NewsRepository

class GetNewsHeadlinesUseCase(private val newsRepository: NewsRepository) {

    suspend fun execute(country: String, page: Int, apiKey: String): Resource<APIResponse> {
        return newsRepository.getNewsHeadlines(country, page, apiKey)
    }

}