package com.baymax.quotable.tags_fragment.data

import com.baymax.quotable.api.BaseDataSource
import com.baymax.quotable.api.Services

class TagsRemoteDataSource(
    private val api: Services
) : BaseDataSource() {

    suspend fun fetchTags() = getResult {
        api.getTags()
    }

}