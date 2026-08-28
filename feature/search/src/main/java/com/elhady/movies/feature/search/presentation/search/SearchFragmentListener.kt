package com.elhady.movies.feature.search.presentation.search

interface SearchFragmentListener {
    fun onClickBack()
    fun onClickFilter()
    fun onClickClear()
    fun showResultMovie()
    fun showResultTv()
    fun showResultPeople()
    fun onClickTryAgain()
}
