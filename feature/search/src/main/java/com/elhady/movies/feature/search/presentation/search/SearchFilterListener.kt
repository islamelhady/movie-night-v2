package com.elhady.movies.feature.search.presentation.search

interface SearchFilterListener {
    fun onClickGenre(genreId: Int)
    fun onClickApply()
}
