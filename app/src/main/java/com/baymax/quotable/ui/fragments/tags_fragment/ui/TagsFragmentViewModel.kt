package com.baymax.quotable.ui.fragments.tags_fragment.ui

import androidx.lifecycle.*
import com.baymax.quotable.ui.fragments.tags_fragment.data.TagsFragmentRepository
import javax.inject.Inject

class TagsFragmentViewModel @Inject constructor(private val repo: TagsFragmentRepository):ViewModel() {

    val quotes by lazy { repo.fetchTags() }
}