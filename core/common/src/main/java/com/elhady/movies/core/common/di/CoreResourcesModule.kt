package com.elhady.movies.core.common.di

import com.elhady.movies.core.common.bases.StringsRes
import com.elhady.movies.core.common.resourseshelper.StringsResImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CoreResourcesModule {

    @Binds
    @Singleton
    abstract fun bindStringsRes(stringsResImpl: StringsResImpl): StringsRes
}
