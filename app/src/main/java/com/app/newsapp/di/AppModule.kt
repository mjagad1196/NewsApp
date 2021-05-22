package com.app.newsapp.di

import android.content.Context
import com.app.newsapp.data.local.NewsArticleDao
import com.app.newsapp.data.remote.ApiService
import com.app.newsapp.domain.repository.AppRepository
import com.app.newsapp.domain.repository.AppRepositoryImpl
import com.app.newsapp.utils.AppConstants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class RemoteAppDataSource

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient) : Retrofit = Retrofit.Builder()
        .baseUrl(AppConstants.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()


    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(AppConstants.TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(AppConstants.TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(AppConstants.TIME_OUT, TimeUnit.SECONDS)

        okHttpClient.addInterceptor(HttpLoggingInterceptor().also {
            it.level = HttpLoggingInterceptor.Level.BODY
        })

        return okHttpClient.build()
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)


    @Singleton
    @RemoteAppDataSource
    @Provides
    fun provideIODispatcher() = Dispatchers.IO



}

@Module
@InstallIn(ApplicationComponent::class)
object AppRepositoryModule {

    @Singleton
    @Provides
    fun getAppRepository(@ApplicationContext appContext: Context, apiService: ApiService,
                         @AppModule.RemoteAppDataSource ioDispatcher: CoroutineDispatcher,
                        @DatabaseModule.LocalSource newsDao: NewsArticleDao): AppRepository {
        return AppRepositoryImpl(appContext, apiService, ioDispatcher, newsDao)
    }

}