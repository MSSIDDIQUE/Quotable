package com.baymax.quotable.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.baymax.quotable.ui.fragments.authors_fragment.ui.AuthorsFragmentViewModel
import com.baymax.quotable.ui.fragments.quotes_fragment.ui.QuotesFragmentViewModel
import com.baymax.quotable.ui.fragments.tags_fragment.ui.TagsFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(AuthorsFragmentViewModel::class)
    abstract fun bindAuthorsFragmentViewModel(viewModel: AuthorsFragmentViewModel):ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(QuotesFragmentViewModel::class)
    abstract fun bindQuotesFragmentViewModel(viewModel: QuotesFragmentViewModel):ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TagsFragmentViewModel::class)
    abstract fun bindTagsFragmentViewModel(viewModel: TagsFragmentViewModel):ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}