package com.elhady.movies.presentation.viewmodel.profile

interface ProfileListener {
    fun onClickFavorite()
    fun onClickWatchlist()
    fun onClickWatchHistory()
    fun onClickMyLists()
    fun onClickLogout()
    fun onUserNotLoggedIn()
    fun ocClickLogIn()
}