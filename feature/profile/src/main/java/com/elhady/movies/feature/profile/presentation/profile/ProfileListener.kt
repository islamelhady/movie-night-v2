package com.elhady.movies.feature.profile.presentation.profile

interface ProfileListener {
    fun onClickFavorite()
    fun onClickWatchlist()
    fun onClickWatchHistory()
    fun onClickMyRated()
    fun onClickMyLists()
    fun onClickLogout()
    fun onClickLogin()
    fun onClickRetry()
}
