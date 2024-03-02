package com.elhady.movies.di

import com.elhady.base.NavigationRes
import com.elhady.base.StringsRes
import com.elhady.movies.resoursesHelper.NavigationResImp
import com.elhady.movies.resoursesHelper.StringResImp
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
    abstract fun bindNavigationRes(navigationResImp: NavigationResImp): NavigationRes


    @Binds
    @Singleton
    abstract fun bindStringsRes(stringResImp: StringResImp): StringsRes

}