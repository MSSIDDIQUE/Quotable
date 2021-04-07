package com.baymax.quotable.ui.fragments.authors_fragment.data

import androidx.lifecycle.distinctUntilChanged
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.baymax.quotable.data.resultLiveData
import com.baymax.quotable.utils.PAGE_SIZE

class AuthorsFragmentRepository(
    private val dao: AuthorsDao,
    private val remoteDataSource: AuthorsRemoteDataSource,
    private val pageDataSource: AuthorsPageDataSource
) {

    fun fetchAuthors()= resultLiveData(
            databaseQuery = {
                    dao.getAllAuthors()
            },
            networkCall = {
                remoteDataSource.fetchAuthors()
            },
            saveCallResult = {
                dao.insertAllAuthors(it.results)
            }
    ).distinctUntilChanged()

    fun fetchAuthorsPage() = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            maxSize = 100,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { pageDataSource }
    ).liveData

}