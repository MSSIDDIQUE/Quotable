package com.baymax.quotable

import android.app.Application
import com.baymax.quotable.data.api.ConnectivityInterceptor
import com.baymax.quotable.data.api.ConnectivityInterceptorImpl
import com.baymax.quotable.data.api.Services
import com.baymax.quotable.ui.fragments.authors_fragment.data.AuthorsFragmentRepository
import com.baymax.quotable.ui.fragments.authors_fragment.data.AuthorsPageDataSource
import com.baymax.quotable.ui.fragments.authors_fragment.data.AuthorsRemoteDataSource
import com.baymax.quotable.ui.fragments.authors_fragment.ui.AuthorsFragmentViewModelFactory
import com.baymax.quotable.data.AppDatabase
import com.baymax.quotable.ui.fragments.quotes_fragment.data.*
import com.baymax.quotable.ui.fragments.quotes_fragment.ui.QuotesFragmentViewModelFactory
import com.baymax.quotable.ui.fragments.tags_fragment.data.TagsFragmentRepository
import com.baymax.quotable.ui.fragments.tags_fragment.data.TagsRemoteDataSource
import com.baymax.quotable.ui.fragments.tags_fragment.ui.TagsFragmentViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class App:Application(),KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@App))
        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { instance<AppDatabase>().authorsDao() }
        bind() from singleton { instance<AppDatabase>().quotesDao() }
        bind() from singleton { instance<AppDatabase>().tagsDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { Services(instance())}
        bind() from singleton { QuotesPageDataSource(instance()) }
        bind() from singleton { QuotesRemoteDataSource(instance()) }
        bind() from singleton { AuthorsPageDataSource(instance())}
        bind() from singleton { AuthorsRemoteDataSource(instance()) }
        bind() from singleton { TagsRemoteDataSource(instance()) }
        bind() from singleton { QuotesFragmentRepository(instance(),instance(),instance()) }
        bind() from singleton { AuthorsFragmentRepository(instance(),instance(),instance()) }
        bind() from singleton { TagsFragmentRepository(instance(),instance()) }
        bind() from provider { QuotesFragmentViewModelFactory(instance()) }
        bind() from provider { AuthorsFragmentViewModelFactory(instance()) }
        bind() from provider { TagsFragmentViewModelFactory(instance()) }
    }

    override fun onCreate() {
        super.onCreate()
    }
}