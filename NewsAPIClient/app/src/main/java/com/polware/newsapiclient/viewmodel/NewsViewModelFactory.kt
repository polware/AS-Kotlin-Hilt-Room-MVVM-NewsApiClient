package com.polware.newsapiclient.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.polware.newsapiclient.viewmodel.usecases.*

class NewsViewModelFactory(private val app: Application,
                           private val getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase,
                           private val searchNewsUseCase: SearchNewsUseCase,
                           private val saveNewsUseCase: SaveNewsUseCase,
                           private val getSavedNewsUseCase: GetSavedNewsUseCase,
                           private val deleteSavedNewsUseCase: DeleteSavedNewsUseCase
):
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(app, getNewsHeadlinesUseCase, searchNewsUseCase,
            saveNewsUseCase, getSavedNewsUseCase, deleteSavedNewsUseCase) as T
    }

}