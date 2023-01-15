package com.polware.newsapiclient.viewmodel.usecases

import com.polware.newsapiclient.data.models.Article
import com.polware.newsapiclient.viewmodel.repositories.NewsRepository

class SaveNewsUseCase(private val newsRepository: NewsRepository) {

    suspend fun execute(article: Article) = newsRepository.saveNews(article)

}