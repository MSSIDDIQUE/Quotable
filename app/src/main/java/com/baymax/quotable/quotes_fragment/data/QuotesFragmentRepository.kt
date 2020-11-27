package com.baymax.quotable.quotes_fragment.data

import androidx.lifecycle.distinctUntilChanged
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.baymax.quotable.api.Request
import com.baymax.quotable.api.Services
import com.baymax.quotable.data.resultLiveData
import com.baymax.quotable.utils.PAGE_SIZE

class QuotesFragmentRepository(
        private val api:Services,
        private val dao: QuotesDao,
        private val remoteDataSource: QuotesRemoteDataSource
) {
    fun fetchQuotes()= resultLiveData(
            databaseQuery = {
                    dao.getAllQuotes()
            },
            networkCall = {
                remoteDataSource.fetchQuotes()
            },
            saveCallResult = {
                dao.insertAllQuotes(it.results)
            }
    ).distinctUntilChanged()

    fun fetchQuotesPage(request:Request?=null) = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            maxSize = 100,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            QuotesPageDataSource(
                    api,
                    request
            )
        }
    ).liveData

}