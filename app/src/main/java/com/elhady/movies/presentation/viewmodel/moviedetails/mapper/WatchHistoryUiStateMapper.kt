package com.elhady.movies.presentation.viewmodel.moviedetails.mapper

import com.elhady.movies.core.domain.entities.MovieInWatchHistoryEntity
import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.domain.entities.moviedetails.MovieDetailsEntity
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
