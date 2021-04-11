package com.baymax.quotable.api

import com.baymax.quotable.ui.fragments.authors_fragment.data.Author
import com.baymax.quotable.ui.fragments.quotes_fragment.data.Quote
import com.baymax.quotable.ui.fragments.tags_fragment.data.Tag
import com.baymax.quotable.utils.PAGE_SIZE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Services {

    companion object{
        const val ENDPOINT = "https://api.quotable.io/"
    }

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
}