package com.elhady.movies.core.data.mappers.domain.movie

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.database.dto.movie.MovieInWatchHistoryLocalDto
import com.elhady.movies.core.domain.model.MovieInWatchHistoryEntity
import javax.inject.Inject

class DomainInWatchHistoryMoviesMapper @Inject constructor()
    : Mapper<MovieInWatchHistoryLocalDto, MovieInWatchHistoryEntity> {
    override fun map(input: MovieInWatchHistoryLocalDto): MovieInWatchHistoryEntity {
        return MovieInWatchHistoryEntity(
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
