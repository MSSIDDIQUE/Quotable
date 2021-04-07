package com.baymax.quotable.ui.fragments.tags_fragment.data

import com.baymax.quotable.data.api.BaseDataSource
import com.baymax.quotable.data.api.Services

class TagsRemoteDataSource(
    private val api: Services
) : BaseDataSource() {

    suspend fun fetchTags() = getResult {
        api.getTags()
    }

}