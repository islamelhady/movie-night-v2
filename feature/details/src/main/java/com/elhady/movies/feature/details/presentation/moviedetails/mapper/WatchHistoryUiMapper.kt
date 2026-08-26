package com.elhady.movies.feature.details.presentation.moviedetails.mapper

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.movie.MovieInWatchHistory
import com.elhady.movies.core.domain.model.movie.MovieDetails
import java.util.Date
import javax.inject.Inject

class WatchHistoryUiMapper @Inject constructor() :
    Mapper<MovieDetails, MovieInWatchHistory> {
    override fun map(input: MovieDetails): MovieInWatchHistory {
        return MovieInWatchHistory(
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
