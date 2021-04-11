package com.baymax.quotable.di

import android.app.Application
import android.content.Context
import com.baymax.quotable.data.AppDatabase
import com.baymax.quotable.api.Services
import com.baymax.quotable.ui.fragments.authors_fragment.data.AuthorsPageDataSource
import com.baymax.quotable.ui.fragments.authors_fragment.data.AuthorsRemoteDataSource
import com.baymax.quotable.ui.fragments.quotes_fragment.data.QuotesPageDataSource
import com.baymax.quotable.ui.fragments.quotes_fragment.data.QuotesRemoteDataSource
import com.baymax.quotable.ui.fragments.tags_fragment.data.TagsRemoteDataSource
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [
    ViewModelModule::class,
    CoreDataModule::class
])
class AppModule {

    /**
     * Network Dependencies
     */

    @Provides
    @Singleton
    fun provideContext(application: Application?): Context? {
        return application
    }

    @QuotableAPI
    @Provides
    fun providePrivateOkHttpClient(
        upstreamClient: OkHttpClient
    ): OkHttpClient {
        return upstreamClient.newBuilder().build()
    }

    private fun createRetrofit(
        okhttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Services.ENDPOINT)
            .client(okhttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }

    private fun <T> provideService(
        okhttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory,
        clazz: Class<T>
    ): T {
        return createRetrofit(okhttpClient, converterFactory).create(clazz)
    }

    @Singleton
    @Provides
    fun provideQuotableService(@QuotableAPI okhttpClient: OkHttpClient,
                               converterFactory: GsonConverterFactory
    ) = provideService(
        okhttpClient,
        converterFactory,
        Services::class.java
    )

    /**
     * Database dependencies.
     */

    @Singleton
    @Provides
    fun provideQuotesDao(db:AppDatabase) = db.quotesDao()

    @Singleton
    @Provides
    fun provideAuthorsDao(db:AppDatabase) = db.authorsDao()

    @Singleton
    @Provides
    fun provideTagsDao(db:AppDatabase) = db.tagsDao()


    /**
     * Repository dependencies.
     */

    @Singleton
    @Provides
    fun provideAuthorRemoteDataSource(
        services: Services
    ) = AuthorsRemoteDataSource(services)

    @Singleton
    @Provides
    fun provideQuoteRemoteDataSource(
        services: Services
    ) = QuotesRemoteDataSource(services)

    @Singleton
    @Provides
    fun provideTagRemoteDataSource(
        services: Services
    ) = TagsRemoteDataSource(services)

    @Singleton
    @Provides
    fun provideAuthorPageDataSource(
        services: Services
    ) = AuthorsPageDataSource(services)

    @Singleton
    @Provides
    fun provideQuotePageDataSource(
        services: Services
    ) = QuotesPageDataSource(services)

}