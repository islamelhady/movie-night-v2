package com.elhady.movies.feature.search.presentation

import com.elhady.movies.core.ui.listener.MovieListener
import com.elhady.movies.core.ui.listener.PeopleListener

interface SearchListener:  MovieListener, PeopleListener {
    fun onClickFilter()
    fun onClickGenre(genresId: Int)
    fun onClickClear()
    fun showResultMovie()
    fun showResultTv()
    fun showResultPeople()
    fun onClickBack()

}
