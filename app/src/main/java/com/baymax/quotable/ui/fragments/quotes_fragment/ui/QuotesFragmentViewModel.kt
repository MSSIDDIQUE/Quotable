package com.baymax.quotable.ui.fragments.quotes_fragment.ui

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.baymax.quotable.data.api.Request
import com.baymax.quotable.ui.fragments.quotes_fragment.data.QuotesFragmentRepository
import javax.inject.Inject

class QuotesFragmentViewModel @Inject constructor(private val repo: QuotesFragmentRepository):ViewModel() {

    private val current_request =  MutableLiveData<Request>()

    val quotes by lazy { repo.fetchQuotes() }

    val quotesPage = current_request.switchMap { repo.fetchQuotesPage(it).cachedIn(viewModelScope) }

    fun updateRequest(request:Request){
        current_request.postValue(request)
    }
}