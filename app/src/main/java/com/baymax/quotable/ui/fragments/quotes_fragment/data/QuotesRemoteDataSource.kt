package com.baymax.quotable.ui.fragments.quotes_fragment.data

import com.baymax.quotable.api.BaseDataSource
import com.baymax.quotable.api.Services

class QuotesRemoteDataSource(
    private val api: Services
) : BaseDataSource() {

    suspend fun fetchQuotes(page: Int? = null) = getResult {
        api.getQuotes(page)
    }

}