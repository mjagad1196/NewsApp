package com.app.newsapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface NewsArticleDao {

    @Insert
    fun insertArticles(articles: List<NewsArticleDb>): List<Long>

    @Query("SELECT * FROM news_article")
    fun getNewsArticles(): List<NewsArticleDb>

    @Query("DELETE FROM news_article")
    fun clearAllArticles()

    @Transaction
    fun clearAndCacheArticles(articles: List<NewsArticleDb>) {
        clearAllArticles()
        insertArticles(articles)
    }

}