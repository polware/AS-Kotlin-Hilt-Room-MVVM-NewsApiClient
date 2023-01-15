package com.polware.newsapiclient.viewmodel.usecases

import com.polware.newsapiclient.data.models.Article
import com.polware.newsapiclient.viewmodel.repositories.NewsRepository

class DeleteSavedNewsUseCase(private val newsRepository: NewsRepository) {

    suspend fun execute(article: Article) = newsRepository.deleteNews(article)

}