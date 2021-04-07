package com.baymax.quotable.ui.fragments.quotes_fragment.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.baymax.quotable.ui.fragments.quotes_fragment.data.QuotesFragmentRepository

class QuotesFragmentViewModelFactory(
    private val repo: QuotesFragmentRepository
): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T: ViewModel?> create(modelClass:Class<T>):T{
        return QuotesFragmentViewModel(repo)   as T
    }
}