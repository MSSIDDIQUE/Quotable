package com.baymax.quotable.quotes_fragment.data

import android.util.Log
import androidx.paging.PagingSource
import com.baymax.quotable.api.Request
import com.baymax.quotable.api.Services
import com.baymax.quotable.utils.COLORS
import retrofit2.HttpException
import java.io.IOException

private const val PAGE_INDEX = 0

class QuotesPageDataSource(
    private val api: Services,
    private val request: Request?=null
):PagingSource<Int,Quote>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Quote> {
        val page = params.key ?: PAGE_INDEX
        return try {
            val response = api.getQuotes(page,request?.author,request?.tag)
            val results = response.body()!!.results
            LoadResult.Page(
                data = results,
                prevKey = if(page==0)null else page-10,
                nextKey = if(results.isEmpty()) null else page+10
            )
        }catch (e:IOException){
            LoadResult.Error(e)
        }catch (e:HttpException){
            LoadResult.Error(e)
        }
    }

}