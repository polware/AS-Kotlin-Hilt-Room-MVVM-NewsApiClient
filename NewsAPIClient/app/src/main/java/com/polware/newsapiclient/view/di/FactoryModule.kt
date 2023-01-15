package com.polware.newsapiclient.view.di

import android.app.Application
import com.polware.newsapiclient.viewmodel.NewsViewModelFactory
import com.polware.newsapiclient.viewmodel.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {

    @Singleton
    @Provides
    fun provideNewsViewModelFactory(application: Application,
                                    getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase,
                                    searchNewsUseCase: SearchNewsUseCase,
                                    saveNewsUseCase: SaveNewsUseCase,
                                    getSavedNewsUseCase: GetSavedNewsUseCase,
                                    deleteSavedNewsUseCase: DeleteSavedNewsUseCase):
            NewsViewModelFactory {

        return NewsViewModelFactory(application, getNewsHeadlinesUseCase, searchNewsUseCase,
            saveNewsUseCase, getSavedNewsUseCase, deleteSavedNewsUseCase)
    }

}