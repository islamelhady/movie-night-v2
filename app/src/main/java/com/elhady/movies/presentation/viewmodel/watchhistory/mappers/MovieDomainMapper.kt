package com.elhady.movies.presentation.viewmodel.watchhistory.mappers

import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.domain.entities.MovieInWatchHistoryEntity
import com.elhady.movies.presentation.viewmodel.watchhistory.MovieUiState
import java.util.Date
import javax.inject.Inject

class MovieDomainMapper @Inject constructor() : Mapper<MovieUiState, MovieInWatchHistoryEntity> {
    override fun map(input: MovieUiState): MovieInWatchHistoryEntity {
        return MovieInWatchHistoryEntity(
            id = input.id ,
            title = input.title,
            description = input.description,
            voteAverage = input.rating,
            posterPath = input.imageUrl,
            dateWatched = Date(),
            year = input.year
        )
    }
}