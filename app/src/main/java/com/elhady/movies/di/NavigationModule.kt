package com.elhady.movies.di

import com.elhady.movies.core.ui.navigation.Navigator
import com.elhady.movies.navigation.NavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
abstract class NavigationModule {

    @Binds
    @ActivityScoped
    abstract fun bindNavigator(navigatorImpl: NavigatorImpl): Navigator
}
