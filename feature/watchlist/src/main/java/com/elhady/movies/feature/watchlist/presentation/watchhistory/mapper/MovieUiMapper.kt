package com.elhady.movies.feature.watchlist.presentation.watchhistory.mapper

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.movie.MovieInWatchHistory
import com.elhady.movies.feature.watchlist.presentation.watchhistory.MovieUiState
import javax.inject.Inject

class MovieUiMapper @Inject constructor() : Mapper<MovieInWatchHistory, MovieUiState> {
    override fun map(input: MovieInWatchHistory): MovieUiState {
        return MovieUiState(
            id = input.id,
            title = input.title,
            description = input.description,
            rating = input.voteAverage,
            imageUrl = input.posterPath,
            dateWatched = input.dateWatched,
            year = input.year
        )
    }
}
