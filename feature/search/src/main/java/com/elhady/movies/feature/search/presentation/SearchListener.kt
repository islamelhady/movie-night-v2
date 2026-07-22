package com.elhady.movies.feature.search.presentation

import com.elhady.movies.core.common.presentation.MovieListener
import com.elhady.movies.core.common.presentation.PeopleListener

interface SearchListener:  MovieListener, PeopleListener {
    fun onClickFilter()
    fun onClickGenre(genresId: Int)
    fun onClickClear()
    fun showResultMovie()
    fun showResultTv()
    fun showResultPeople()
    fun onClickBack()

}
