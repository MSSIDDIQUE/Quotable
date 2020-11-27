package com.baymax.quotable.api

import android.util.Log
import com.baymax.quotable.authors_fragment.data.Author
import com.baymax.quotable.quotes_fragment.data.Quote
import com.baymax.quotable.tags_fragment.data.Tag
import com.baymax.quotable.utils.PAGE_SIZE
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface Services {

    @GET("quotes")
    suspend fun getQuotes(
        @Query("skip") page: Int? = null,
        @Query("author") author: String? = null,
        @Query("tags") tags: String? = null,
        @Query("limit") pageSize: Int = PAGE_SIZE
    ):Response<ApiResponse<Quote>>

    @GET("authors")
    suspend fun getAuthors(
        @Query("skip") page: Int? = null,
        @Query("limit") pageSize: Int? = null
    ):Response<ApiResponse<Author>>

    @GET("tags")
    suspend fun getTags():Response<List<Tag>>

    companion object{
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ):Services{
            var requestInterceptor = Interceptor{chain ->
                val url = chain.request()
                    .url
                    .newBuilder()
                    .build()
                Log.d("(Saquib)","the url is "+url.toString())
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()
                return@Interceptor chain.proceed(request)
            }
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.quotable.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Services::class.java)
        }
    }
}