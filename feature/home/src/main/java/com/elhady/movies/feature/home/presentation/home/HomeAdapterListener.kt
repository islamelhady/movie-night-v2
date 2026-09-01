package com.elhady.movies.feature.home.presentation.home

import com.elhady.movies.core.ui.interaction.MovieAdapterListener
import com.elhady.movies.core.ui.interaction.PeopleAdapterListener
import com.elhady.movies.core.ui.interaction.TvShowAdapterListener

interface HomeAdapterListener : PeopleAdapterListener, MovieAdapterListener, TvShowAdapterListener, HomeListener
