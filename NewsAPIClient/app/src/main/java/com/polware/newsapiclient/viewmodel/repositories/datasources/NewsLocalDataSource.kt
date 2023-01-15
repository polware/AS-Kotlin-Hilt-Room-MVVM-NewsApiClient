package com.polware.newsapiclient.viewmodel.repositories.datasources

import com.polware.newsapiclient.data.models.Article
import kotlinx.coroutines.flow.Flow

interface NewsLocalDataSource {

    suspend fun saveArticleToDB(article: Article)

    fun getSavedArticles(): Flow<List<Article>>

    suspend fun deleteArticleFromDB(article: Article)

}