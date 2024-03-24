package com.elhady.movies.presentation.viewmodel.explore

import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.domain.entities.MovieEntity
import com.elhady.movies.presentation.viewmodel.home.TrendingMoviesUiState
import javax.inject.Inject

class ExploreTrendingUiMapper @Inject constructor() : Mapper<MovieEntity, ExploreUiState.TrendingMoviesUiState> {
    override fun map(input: MovieEntity): ExploreUiState.TrendingMoviesUiState {
        return ExploreUiState.TrendingMoviesUiState(
            id = input.id,
            imageUrl = input.imageUrl,
            rate = input.rate,
            genres = input.genreEntities.joinToString(" | ") { it.genreName },
            year = input.year.split("-")[0],
            title = input.title
        )
    }
}