package com.baymax.quotable.di

import com.baymax.quotable.ui.activities.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBuilderModule {
    @ContributesAndroidInjector(modules = [
        MainActivityModule::class,
        FragmentBuildersModule::class
    ])
    abstract fun bindMainActivity(): MainActivity
}