package com.baymax.quotable.quotes_fragment.ui

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.baymax.quotable.api.Request
import com.baymax.quotable.quotes_fragment.data.QuotesFragmentRepository

class QuotesFragmentViewModel(private val repo: QuotesFragmentRepository):ViewModel() {

    private val current_request =  MutableLiveData<Request>()

    val quotes by lazy { repo.fetchQuotes() }

    val quotesPage = current_request.switchMap { repo.fetchQuotesPage(it).cachedIn(viewModelScope) }

    fun updateRequest(request:Request){
        current_request.postValue(request)
    }
}