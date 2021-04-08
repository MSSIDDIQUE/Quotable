package com.baymax.quotable.ui.fragments.tags_fragment.data

import androidx.lifecycle.distinctUntilChanged
import com.baymax.quotable.data.resultLiveData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TagsFragmentRepository @Inject constructor(
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