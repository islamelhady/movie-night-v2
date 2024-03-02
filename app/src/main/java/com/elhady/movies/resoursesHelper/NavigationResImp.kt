package com.elhady.movies.resoursesHelper

import com.elhady.base.NavigationRes
import com.elhady.movies.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavigationResImp @Inject constructor(): NavigationRes {
    override val homeFeatureLink: Int
        get() = TODO("Not yet implemented")
    override val authFeatureLink: Int
        get() = TODO("Not yet implemented")
    override val profileFeatureLink: Int = R.id.profileFragment
}