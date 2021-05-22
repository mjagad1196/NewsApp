package com.app.newsapp.domain.repository

import android.content.Context
import com.app.newsapp.data.entities.response.NewsResponse
import com.app.newsapp.data.local.NewsArticleDao
import com.app.newsapp.data.local.NewsArticleDb
import com.app.newsapp.data.remote.ApiService
import com.app.newsapp.utils.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException

class AppRepositoryImpl(private val context: Context, private val apiService: ApiService,
                        private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
                        private val newsDao: NewsArticleDao): AppRepository, NewsMapper {
    override suspend fun getNews(): OperationResult<NewsResponse?> = withContext(ioDispatcher){
        try {
            if (!CommonUtils.hasNetwork(context)){
                throw NetworkException("No internet connection!!")
            }
            val response = apiService.getTopHeadlines()
            if (response.isSuccessful){
                return@withContext OperationResult.Success(response.body())
            } else {
                return@withContext OperationResult.Error(Exception("Something went wrong"), response.code())
            }
        } catch (exception: Exception){
            catchException(exception)
        }
    }

    override suspend fun getNewsFromCache(): OperationResult<List<NewsArticleDb>> = withContext(ioDispatcher) {
        try {
            val newNews = getNews()
            newNews.let { operationResult ->
                when(operationResult){
                    is OperationResult.Success -> {
                        operationResult.data?.articles?.toStorage()?.let(newsDao::clearAndCacheArticles)
                    }

                    is OperationResult.Error -> {
                        //return@withContext OperationResult.Error(operationResult.exception, operationResult.errorCode)
                    }
                }
            }

            val cachedNews = newsDao.getNewsArticles()
            if (cachedNews.isNotEmpty()){
                return@withContext OperationResult.Success(cachedNews)
            } else {
                return@withContext OperationResult.Error(Exception("No data available"), null)
            }
        } catch (exception: Exception){
            catchException(exception)
        }
    }

    private suspend fun catchException(exception: Exception): OperationResult.Error =
        withContext(ioDispatcher) {
            when(exception) {
                is NetworkException -> return@withContext  OperationResult.Error(exception, null)
                is SocketTimeoutException -> return@withContext OperationResult.Error(
                    Exception("Your internet connection seems to be slow or connection with server is not established"),
                    null
                )
                else -> return@withContext OperationResult.Error(
                    Exception("Something went wrong"), null
                )
            }
        }
}