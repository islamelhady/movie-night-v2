package com.elhady.movies.core.data.mapper.movie

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.database.entity.movie.MovieInWatchHistoryEntity
import com.elhady.movies.core.domain.model.movie.MovieInWatchHistory
import javax.inject.Inject

class WatchHistoryMoviesToEntityMapper @Inject constructor() :
    Mapper<MovieInWatchHistory, MovieInWatchHistoryEntity> {

    override fun map(input: MovieInWatchHistory): MovieInWatchHistoryEntity {
        return MovieInWatchHistoryEntity(
            id = input.id,
            posterPath = input.posterPath,
            title = input.title,
            voteAverage = input.voteAverage,
            description = input.description,
            dateWatched = input.dateWatched,
            year = input.year
        )
    }
}
