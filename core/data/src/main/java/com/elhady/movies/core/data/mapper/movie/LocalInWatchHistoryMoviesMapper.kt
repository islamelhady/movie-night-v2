package com.elhady.movies.core.data.mapper.movie

import com.elhady.movies.core.domain.model.movie.MovieInWatchHistoryEntity
import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.database.entity.movie.MovieInWatchHistoryEntity
import javax.inject.Inject

class LocalInWatchHistoryMoviesMapper @Inject constructor()
    : Mapper<MovieInWatchHistoryEntity, com.elhady.movies.core.database.entity.movie.MovieInWatchHistoryEntity> {
    override fun map(input: MovieInWatchHistoryEntity): com.elhady.movies.core.database.entity.movie.MovieInWatchHistoryEntity {
        return com.elhady.movies.core.database.entity.movie.MovieInWatchHistoryEntity(
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
