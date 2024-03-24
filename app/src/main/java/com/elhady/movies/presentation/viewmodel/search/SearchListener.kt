package com.elhady.movies.presentation.viewmodel.search

import com.elhady.movies.presentation.viewmodel.common.listener.MovieListener
import com.elhady.movies.presentation.viewmodel.common.listener.PeopleListener

interface SearchListener:  MovieListener, PeopleListener {
    fun onClickFilter()
    fun onClickGenre(genresId: Int)
    fun onClickClear()
    fun showResultMovie()
    fun showResultTv()
    fun showResultPeople()

}