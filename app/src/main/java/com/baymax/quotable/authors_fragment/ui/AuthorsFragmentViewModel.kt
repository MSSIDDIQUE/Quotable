package com.baymax.quotable.authors_fragment.ui

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.baymax.quotable.authors_fragment.data.AuthorsFragmentRepository

class AuthorsFragmentViewModel(private val repo: AuthorsFragmentRepository):ViewModel() {
    val authors by lazy { repo.fetchAuthors() }

    val authorsPage by lazy { repo.fetchAuthorsPage().cachedIn(viewModelScope) }
}