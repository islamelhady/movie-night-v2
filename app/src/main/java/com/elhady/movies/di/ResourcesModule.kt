package com.elhady.movies.di

import com.elhady.movies.core.bases.NavigationRes
import com.elhady.movies.core.bases.StringsRes
import com.elhady.movies.core.resourseshelper.NavigationResImpl
import com.elhady.movies.core.resourseshelper.StringsResImpl
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
    abstract fun bindStringsRes(stringsResImpl: StringsResImpl): StringsRes

    @Binds
    @Singleton
    abstract fun bindNavigationRes(navigationResImpl: NavigationResImpl): NavigationRes
}