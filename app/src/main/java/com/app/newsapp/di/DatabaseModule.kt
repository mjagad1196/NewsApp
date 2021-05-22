package com.app.newsapp.di

import android.app.Application
import com.app.newsapp.data.local.NewsArticleDao
import com.app.newsapp.data.local.NewsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class LocalSource

    @Singleton
    @Provides
    fun provideDb(app: Application): NewsDatabase = NewsDatabase.buildDefault(app)

    @Singleton
    @DatabaseModule.LocalSource
    @Provides
    fun provideNewsArticleDao(db: NewsDatabase): NewsArticleDao = db.newsArticlesDao()

}