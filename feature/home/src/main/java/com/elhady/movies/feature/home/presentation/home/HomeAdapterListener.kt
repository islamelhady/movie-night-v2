package com.elhady.movies.feature.home.presentation.home

import com.elhady.movies.core.ui.interaction.PeopleAdapterListener

interface HomeAdapterListener: PeopleAdapterListener{
    fun onMovieClick(id: Int)
    fun onTvShowClick(id: Int)
}