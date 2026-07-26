package com.elhady.movies.presentation.resourseshelper

import com.elhady.movies.core.ui.listener.NavigationRes
import com.elhady.movies.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavigationResImpl @Inject constructor() : NavigationRes {
    override val homeFeatureLink = R.id.homeFragment
    override val authFeatureLink = R.id.loginFragment
    override val profileFeatureLink: Int = R.id.profileFragment
}
