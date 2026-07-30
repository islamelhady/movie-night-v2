package com.elhady.movies.di

import com.elhady.movies.core.ui.interaction.NavigationRes
import com.elhady.movies.resourceshelper.NavigationResImpl
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
