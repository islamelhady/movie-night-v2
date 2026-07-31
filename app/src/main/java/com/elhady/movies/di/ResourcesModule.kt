package com.elhady.movies.di

import com.elhady.movies.core.ui.resource.NavigationRes
import com.elhady.movies.resource.NavigationResImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ResourcesModule {

    @Binds
    @Singleton
    abstract fun bindNavigationRes(navigationResImpl: NavigationResImpl): NavigationRes
}
