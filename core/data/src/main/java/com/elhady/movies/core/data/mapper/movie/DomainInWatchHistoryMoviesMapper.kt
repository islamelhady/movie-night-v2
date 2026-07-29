package com.elhady.movies.core.data.mapper.movie

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.database.entity.movie.MovieInWatchHistoryEntity
import com.elhady.movies.core.domain.model.movie.MovieInWatchHistoryEntity
import javax.inject.Inject

class DomainInWatchHistoryMoviesMapper @Inject constructor()
    : Mapper<MovieInWatchHistoryEntity, MovieInWatchHistoryEntity> {
    override fun map(input: MovieInWatchHistoryEntity): MovieInWatchHistoryEntity {
        return com.elhady.movies.core.domain.model.movie.MovieInWatchHistoryEntity(
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
