package com.polware.newsapiclient.viewmodel.repositories

import com.polware.newsapiclient.data.models.APIResponse
import com.polware.newsapiclient.data.models.Article
import com.polware.newsapiclient.data.utils.Resource
import com.polware.newsapiclient.viewmodel.repositories.datasources.NewsLocalDataSource
import com.polware.newsapiclient.viewmodel.repositories.datasources.NewsRemoteDataSource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class NewsRepositoryImpl(private val newsRemoteDataSource: NewsRemoteDataSource,
                         private val newsLocalDataSource: NewsLocalDataSource): NewsRepository {

    override suspend fun getNewsHeadlines(country: String, page: Int, apiKey: String):
            Resource<APIResponse> {
        return responseToResource(newsRemoteDataSource.getNewsTopHeadlines(country, page, apiKey))
    }

    override suspend fun searchNews(searchQuery: String, apiKey: String): Resource<APIResponse> {
        return responseToResource(newsRemoteDataSource.searchFromEverything(searchQuery, apiKey))
    }

    override fun getSavedNews(): Flow<List<Article>> {
        return newsLocalDataSource.getSavedArticles()
    }

    override suspend fun saveNews(article: Article) {
        newsLocalDataSource.saveArticleToDB(article)
    }

    override suspend fun deleteNews(article: Article) {
        newsLocalDataSource.deleteArticleFromDB(article)
    }

    // This function converts Response object to Resource object
    private fun responseToResource(response: Response<APIResponse>): Resource<APIResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                    result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

}