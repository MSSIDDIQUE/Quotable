package com.baymax.quotable.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.baymax.quotable.ui.fragments.authors_fragment.data.Author
import com.baymax.quotable.ui.fragments.authors_fragment.data.AuthorsDao
import com.baymax.quotable.ui.fragments.quotes_fragment.data.Quote
import com.baymax.quotable.ui.fragments.quotes_fragment.data.QuotesDao
import com.baymax.quotable.ui.fragments.tags_fragment.data.Tag
import com.baymax.quotable.ui.fragments.tags_fragment.data.TagsDao

@Database(
    entities = [Quote::class, Author::class, Tag::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun quotesDao() : QuotesDao
    abstract fun authorsDao() : AuthorsDao
    abstract fun tagsDao(): TagsDao

    companion object{
        @Volatile private var instance: AppDatabase ?= null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance?: synchronized(LOCK){
            instance?: buildDatabase(context).also {
                instance = it
            }
        }
        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
            AppDatabase::class.java, "quotable.db")
                .build()
    }
}