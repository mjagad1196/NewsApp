package com.app.newsapp.data.local

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.newsapp.data.local.NewsArticleDb.NewsArticles.tableName

@Entity(tableName = tableName)
data class NewsArticleDb(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = NewsArticles.Column.author)
    val author: String? = null,

    @ColumnInfo(name = NewsArticles.Column.title)
    val title: String? = null,

    @ColumnInfo(name = NewsArticles.Column.description)
    val description: String? = null,

    @ColumnInfo(name = NewsArticles.Column.url)
    val url: String? = null,

    @ColumnInfo(name = NewsArticles.Column.urlToImage)
    val urlToImage: String? = null,

    @ColumnInfo(name = NewsArticles.Column.publishedAt)
    val publishedAt: String? = null,

    @Embedded(prefix = "source_")
    val source: Source,

    @ColumnInfo(name = NewsArticles.Column.content)
    val content: String? = null

) : Parcelable {

    data class Source(
        @ColumnInfo(name = NewsArticles.Column.sourceId)
        val id: String? = null,

        @ColumnInfo(name = NewsArticles.Column.sourceName)
        val name: String? = null
    ) : Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString()
        ) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(id)
            parcel.writeString(name)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Source> {
            override fun createFromParcel(parcel: Parcel): Source {
                return Source(parcel)
            }

            override fun newArray(size: Int): Array<Source?> {
                return arrayOfNulls(size)
            }
        }
    }

    object NewsArticles{

        const val tableName = "news_article"

        object Column {
            const val id = "id"
            const val author = "author"
            const val title = "title"
            const val description = "description"
            const val url = "url"
            const val urlToImage = "urlToImage"
            const val publishedAt = "publishedAt"
            const val content = "content"
            const val sourceId = "sourceId"
            const val sourceName = "sourceName"
        }

    }

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        TODO("source"),
        parcel.readString()
    ) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel?, flags: Int) {
        parcel?.let {
            it.writeInt(id)
            it.writeString(author)
            it.writeString(title)
            it.writeString(description)
            it.writeString(url)
            it.writeString(urlToImage)
            it.writeString(publishedAt)
            it.writeString(content)
            it.writeParcelable(source, 0)
        }
    }

    companion object CREATOR : Parcelable.Creator<NewsArticleDb> {
        override fun createFromParcel(parcel: Parcel): NewsArticleDb {
            return NewsArticleDb(parcel)
        }

        override fun newArray(size: Int): Array<NewsArticleDb?> {
            return arrayOfNulls(size)
        }
    }

}