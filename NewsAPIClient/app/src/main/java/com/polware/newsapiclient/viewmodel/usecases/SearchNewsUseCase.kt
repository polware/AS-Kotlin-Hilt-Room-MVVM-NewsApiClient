package com.polware.newsapiclient.viewmodel.usecases

import com.polware.newsapiclient.data.models.APIResponse
import com.polware.newsapiclient.data.utils.Resource
import com.polware.newsapiclient.viewmodel.repositories.NewsRepository

class SearchNewsUseCase(private val newsRepository: NewsRepository) {

    suspend fun execute(searchQuery: String, apiKey: String): Resource<APIResponse> {
        return newsRepository.searchNews(searchQuery, apiKey)
    }

}