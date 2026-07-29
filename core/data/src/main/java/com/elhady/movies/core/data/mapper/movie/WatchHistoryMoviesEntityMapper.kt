package com.elhady.movies.core.data.mapper.movie

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.database.entity.movie.MovieInWatchHistoryEntity
import com.elhady.movies.core.domain.model.movie.MovieInWatchHistory
import javax.inject.Inject

class WatchHistoryMoviesEntityMapper @Inject constructor()
    : Mapper<MovieInWatchHistoryEntity, MovieInWatchHistory> {

    override fun map(input: MovieInWatchHistoryEntity): MovieInWatchHistory {
        return MovieInWatchHistory(
            id = input.id,
            title = input.title,
            description = input.description,
            voteAverage = input.voteAverage,
            dateWatched = input.dateWatched,
            posterPath = input.posterPath,
            year = input.year
        )
    }
}
