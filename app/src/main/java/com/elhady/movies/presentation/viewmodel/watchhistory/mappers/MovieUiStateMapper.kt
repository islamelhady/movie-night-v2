package com.elhady.movies.presentation.viewmodel.watchhistory.mappers

import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.domain.entities.MovieInWatchHistoryEntity
import com.elhady.movies.presentation.viewmodel.watchhistory.MovieUiState
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