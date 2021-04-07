package com.baymax.quotable.ui.fragments.authors_fragment.data

import com.baymax.quotable.data.api.BaseDataSource
import com.baymax.quotable.data.api.Services

class AuthorsRemoteDataSource(
    private val api: Services
) : BaseDataSource() {

    suspend fun fetchAuthors(page: Int? = null) = getResult {
        api.getAuthors(page)
    }

}