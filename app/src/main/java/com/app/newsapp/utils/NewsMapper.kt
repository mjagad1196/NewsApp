package com.app.newsapp.utils

import com.app.newsapp.data.entities.response.Source
import com.app.newsapp.data.entities.response.Articles
import com.app.newsapp.data.local.NewsArticleDb

interface NewsMapper: Mapper<NewsArticleDb, Articles> {

    override fun NewsArticleDb.toRemote(): Articles {
        return Articles(
            author = author,
            title = title,
            description = description,
            url = url,
            urlToImage = urlToImage,
            publishedAt = publishedAt,
            content = content,
            source = Source(source.id, source.name)
        )
    }

    override fun Articles.toStorage(): NewsArticleDb {
        return NewsArticleDb(
            author = author,
            title = title,
            description = description,
            url = url,
            urlToImage = urlToImage,
            publishedAt = publishedAt,
            content = content,
            source = NewsArticleDb.Source(source.id, source.name)
        )
    }

}