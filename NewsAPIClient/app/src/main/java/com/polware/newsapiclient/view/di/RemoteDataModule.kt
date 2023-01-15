package com.polware.newsapiclient.view.di

import com.polware.newsapiclient.data.api.NewsApiService
import com.polware.newsapiclient.viewmodel.repositories.datasources.NewsRemoteDataSource
import com.polware.newsapiclient.viewmodel.repositories.datasources.NewsRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataModule {

    @Singleton
    @Provides
    fun provideNewsRemoteDataSource(newsApiService: NewsApiService): NewsRemoteDataSource {
        return NewsRemoteDataSourceImpl(newsApiService)
    }

}