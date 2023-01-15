package com.polware.newsapiclient.view.di

import com.polware.newsapiclient.viewmodel.repositories.NewsRepository
import com.polware.newsapiclient.viewmodel.repositories.NewsRepositoryImpl
import com.polware.newsapiclient.viewmodel.repositories.datasources.NewsLocalDataSource
import com.polware.newsapiclient.viewmodel.repositories.datasources.NewsRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideNewsRepository(newsRemoteDataSource: NewsRemoteDataSource,
                              newsLocalDataSource: NewsLocalDataSource): NewsRepository {
        return NewsRepositoryImpl(newsRemoteDataSource, newsLocalDataSource)
    }

}