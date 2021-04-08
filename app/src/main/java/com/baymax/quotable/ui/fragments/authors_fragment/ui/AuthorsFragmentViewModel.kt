package com.baymax.quotable.ui.fragments.authors_fragment.ui

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.baymax.quotable.ui.fragments.authors_fragment.data.AuthorsFragmentRepository
import javax.inject.Inject

class AuthorsFragmentViewModel @Inject constructor(private val repo: AuthorsFragmentRepository):ViewModel() {
    val authors by lazy { repo.fetchAuthors() }

    val authorsPage by lazy { repo.fetchAuthorsPage().cachedIn(viewModelScope) }
}