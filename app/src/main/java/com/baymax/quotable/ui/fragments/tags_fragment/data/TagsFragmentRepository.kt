package com.baymax.quotable.ui.fragments.tags_fragment.data

import androidx.lifecycle.distinctUntilChanged
import com.baymax.quotable.data.resultLiveData

class TagsFragmentRepository(
        private val dao: TagsDao,
        private val remoteDataSource: TagsRemoteDataSource
) {
    fun fetchTags()= resultLiveData(
            databaseQuery = {
                    dao.getAllTags()
            },
            networkCall = {
                remoteDataSource.fetchTags()
            },
            saveCallResult = {
                dao.insertAllTags(it)
            }
    ).distinctUntilChanged()


}