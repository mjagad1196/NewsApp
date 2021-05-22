package com.app.newsapp.utils

import com.app.newsapp.data.local.NewsArticleDb

interface ItemClickListener {

    fun onItemClick(position: Int)
}