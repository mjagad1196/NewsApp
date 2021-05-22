package com.app.newsapp.data.remote

import com.app.newsapp.data.entities.response.NewsResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("top-headlines?sources=techcrunch&apiKey=a7203fd6f1ab4fadbf5e6758d627f941")
    suspend fun getTopHeadlines():Response<NewsResponse>

}