package com.app.newsapp.domain.repository

import com.app.newsapp.data.entities.response.NewsResponse
import com.app.newsapp.data.local.NewsArticleDb
import com.app.newsapp.utils.OperationResult

interface AppRepository {

    suspend fun getNews(): OperationResult<NewsResponse?>

    suspend fun getNewsFromCache(): OperationResult<List<NewsArticleDb>>
}