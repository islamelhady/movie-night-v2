package com.elhady.movies.feature.explore.viewmodel.explore

import com.elhady.movies.core.domain.model.movie.Movie
import com.elhady.movies.core.common.mapper.Mapper
import javax.inject.Inject

class ExploreTrendingUiMapper @Inject constructor() :
    Mapper<Movie, ExploreUiState.TrendingMoviesUiState> {
    override fun map(input: Movie): ExploreUiState.TrendingMoviesUiState {
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
