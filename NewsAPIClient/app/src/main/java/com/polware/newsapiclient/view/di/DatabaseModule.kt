package com.polware.newsapiclient.view.di

import android.app.Application
import androidx.room.Room
import com.polware.newsapiclient.data.database.ArticleDAO
import com.polware.newsapiclient.data.database.ArticleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideArticleDatabase(app: Application): ArticleDatabase {
        return Room.databaseBuilder(app, ArticleDatabase::class.java, "article_db")
            .fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideArticleDao(articleDatabase: ArticleDatabase): ArticleDAO {
        return articleDatabase.getArticleDao()
    }

}