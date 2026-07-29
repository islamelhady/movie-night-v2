package com.elhady.movies.feature.details.presentation.moviedetails.mapper

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.movie.MovieInWatchHistoryEntity
import com.elhady.movies.core.domain.model.movie.MovieDetailsEntity
import java.util.Date
import javax.inject.Inject

class WatchHistoryUiStateMapper @Inject constructor() :
    Mapper<MovieDetailsEntity, MovieInWatchHistoryEntity> {
    override fun map(input: MovieDetailsEntity): MovieInWatchHistoryEntity {
        return MovieInWatchHistoryEntity(
            id = input.id,
            posterPath = input.backdropPath,
            title = input.title,
            voteAverage = input.voteAverage,
            description = input.overview,
            dateWatched = Date(),
            year = input.year.takeIf { it.isNotBlank() }?.split("-")?.get(0)?.toInt() ?: 1911
        )
    }
}
