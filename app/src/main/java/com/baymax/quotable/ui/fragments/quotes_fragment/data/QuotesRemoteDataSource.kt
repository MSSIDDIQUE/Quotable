package com.baymax.quotable.ui.fragments.quotes_fragment.data

import com.baymax.quotable.data.api.BaseDataSource
import com.baymax.quotable.data.api.Services

class QuotesRemoteDataSource(
    private val api: Services
) : BaseDataSource() {

    suspend fun fetchQuotes(page: Int? = null) = getResult {
        api.getQuotes(page)
    }

}