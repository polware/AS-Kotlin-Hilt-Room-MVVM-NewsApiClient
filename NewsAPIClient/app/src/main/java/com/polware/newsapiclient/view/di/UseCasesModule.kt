package com.polware.newsapiclient.view.di

import com.polware.newsapiclient.viewmodel.repositories.NewsRepository
import com.polware.newsapiclient.viewmodel.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCasesModule {

    @Singleton
    @Provides
    fun provideGetNewsHeadlinesUsesCase(newsRepository: NewsRepository): GetNewsHeadlinesUseCase {
        return GetNewsHeadlinesUseCase(newsRepository)
    }

    @Singleton
    @Provides
    fun provideSearchFromEverything(newsRepository: NewsRepository): SearchNewsUseCase {
        return SearchNewsUseCase(newsRepository)
    }

    @Singleton
    @Provides
    fun provideSaveNewsUseCase(newsRepository: NewsRepository): SaveNewsUseCase {
        return SaveNewsUseCase(newsRepository)
    }

    @Singleton
    @Provides
    fun provideGetSavedNewsUseCase(newsRepository: NewsRepository): GetSavedNewsUseCase {
        return GetSavedNewsUseCase(newsRepository)
    }

    @Singleton
    @Provides
    fun provideDeleteNewsUseCase(newsRepository: NewsRepository): DeleteSavedNewsUseCase {
        return DeleteSavedNewsUseCase(newsRepository)
    }

}