package com.app.newsapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [NewsArticleDb::class],
    version = 1
)
abstract class NewsDatabase: RoomDatabase() {

    abstract fun newsArticlesDao(): NewsArticleDao

    companion object {
        private const val databaseName = "news-db"

        fun buildDefault(context: Context) =
            Room.databaseBuilder(context, NewsDatabase::class.java, databaseName)
                .addMigrations()
                .build()


    }

}