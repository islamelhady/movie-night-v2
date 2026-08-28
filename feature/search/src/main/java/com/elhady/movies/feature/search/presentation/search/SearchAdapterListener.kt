package com.elhady.movies.feature.search.presentation.search

import com.elhady.movies.core.ui.interaction.MovieAdapterListener
import com.elhady.movies.core.ui.interaction.PeopleAdapterListener

interface SearchAdapterListener:  MovieAdapterListener, PeopleAdapterListener {
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
