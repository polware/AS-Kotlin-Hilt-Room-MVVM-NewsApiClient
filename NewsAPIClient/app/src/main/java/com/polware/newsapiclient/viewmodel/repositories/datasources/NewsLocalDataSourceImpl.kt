package com.polware.newsapiclient.viewmodel.repositories.datasources

import com.polware.newsapiclient.data.database.ArticleDAO
import com.polware.newsapiclient.data.models.Article
import kotlinx.coroutines.flow.Flow

class NewsLocalDataSourceImpl(private val articleDAO: ArticleDAO): NewsLocalDataSource {

    override suspend fun saveArticleToDB(article: Article) {
        articleDAO.insert(article)
    }

    override fun getSavedArticles(): Flow<List<Article>> {
        return articleDAO.getAllArticles()
    }

    override suspend fun deleteArticleFromDB(article: Article) {
        articleDAO.deleteArticle(article)
    }

}