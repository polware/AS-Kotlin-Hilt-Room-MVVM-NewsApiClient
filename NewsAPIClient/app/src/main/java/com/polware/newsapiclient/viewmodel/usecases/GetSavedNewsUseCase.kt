package com.polware.newsapiclient.viewmodel.usecases

import com.polware.newsapiclient.data.models.Article
import com.polware.newsapiclient.viewmodel.repositories.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetSavedNewsUseCase(private val newsRepository: NewsRepository) {

    fun execute(): Flow<List<Article>> {
        return newsRepository.getSavedNews()
    }

}