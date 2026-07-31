package com.elhady.movies.feature.profile.presentation.profile

interface ProfileListener {
    fun onClickFavorite()
    fun onClickWatchlist()
    fun onClickWatchHistory()
    fun onClickMyLists()
    fun onClickLogout()
    fun onUserNotLoggedIn()
    fun ocClickLogIn()
}