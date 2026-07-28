package com.elhady.movies.feature.watchlist.presentation.watchhistory.mappers

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.movie.MovieInWatchHistoryEntity
import com.elhady.movies.feature.watchlist.presentation.watchhistory.MovieUiState
import javax.inject.Inject

class MovieUiStateMapper @Inject constructor() : Mapper<MovieInWatchHistoryEntity, MovieUiState> {
    override fun map(input: MovieInWatchHistoryEntity): MovieUiState {
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
