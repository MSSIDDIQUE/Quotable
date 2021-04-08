package com.baymax.quotable.ui.fragments.authors_fragment.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.baymax.quotable.data.api.Services
import retrofit2.HttpException
import java.io.IOException



class AuthorsPageDataSource(
    private val api: Services
): PagingSource<Int, Author>() {

    companion object{
        private const val PAGE_INDEX = 10
    }
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Author> {
        val page = params.key ?: PAGE_INDEX
        return try {
            val response = api.getAuthors(page)
            val results = response.body()!!.results
            LoadResult.Page(
                data = results,
                prevKey = if(page==10)null else page-10,
                nextKey = if(results.isEmpty()) null else page+10
            )
        }catch (e: IOException){
            LoadResult.Error(e)
        }catch (e: HttpException){
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Author>): Int? {
        TODO("Not yet implemented")
    }
}