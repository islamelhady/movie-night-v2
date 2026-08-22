package com.elhady.movies.feature.search.presentation.search

import com.elhady.movies.core.ui.interaction.MovieListener
import com.elhady.movies.core.ui.interaction.PeopleListener

interface SearchListener:  MovieListener, PeopleListener {
    fun onClickFilter()
    fun onClickGenre(genresId: Int)
    fun onClickClear()
    fun showResultMovie()
    fun showResultTv()
    fun showResultPeople()
    fun onClickBack()
    fun onClickTryAgain()
    fun onClickApply()
}
