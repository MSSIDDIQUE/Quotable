package com.baymax.quotable.tags_fragment.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.baymax.quotable.tags_fragment.data.TagsFragmentRepository
import com.baymax.quotable.tags_fragment.ui.TagsFragmentViewModel

class TagsFragmentViewModelFactory(
    private val repo: TagsFragmentRepository
): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T: ViewModel?> create(modelClass:Class<T>):T{
        return TagsFragmentViewModel(repo)   as T
    }
}