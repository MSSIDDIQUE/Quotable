package com.baymax.quotable.di

import com.baymax.quotable.ui.fragments.authors_fragment.ui.AuthorsFragment
import com.baymax.quotable.ui.fragments.home_fragment.HomeFragment
import com.baymax.quotable.ui.fragments.quotes_fragment.ui.QuotesFragment
import com.baymax.quotable.ui.fragments.tags_fragment.ui.TagsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeQuotesFragment(): QuotesFragment

    @ContributesAndroidInjector
    abstract fun contributeAuthorsFragment(): AuthorsFragment

    @ContributesAndroidInjector
    abstract fun contributeTagsFragment(): TagsFragment
}