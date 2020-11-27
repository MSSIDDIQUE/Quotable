package com.baymax.quotable.authors_fragment.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.baymax.quotable.authors_fragment.data.AuthorsFragmentRepository

class AuthorsFragmentViewModelFactory(
    private val repo: AuthorsFragmentRepository
): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T: ViewModel?> create(modelClass:Class<T>):T{
        return AuthorsFragmentViewModel(repo)   as T
    }
}