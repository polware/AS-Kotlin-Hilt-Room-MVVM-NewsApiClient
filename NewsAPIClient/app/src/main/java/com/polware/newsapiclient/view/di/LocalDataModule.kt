package com.polware.newsapiclient.view.di

import com.polware.newsapiclient.data.database.ArticleDAO
import com.polware.newsapiclient.viewmodel.repositories.datasources.NewsLocalDataSource
import com.polware.newsapiclient.viewmodel.repositories.datasources.NewsLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataModule {

    @Singleton
    @Provides
    fun provideLocalDataSource(articleDAO: ArticleDAO): NewsLocalDataSource {
        return NewsLocalDataSourceImpl(articleDAO)
    }

}