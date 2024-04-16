package com.elhady.movies.core.data.repository.mappers.domain.movie

import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.data.local.database.dto.movie.MovieInWatchHistoryLocalDto
import com.elhady.movies.core.domain.entities.MovieInWatchHistoryEntity
import javax.inject.Inject

class DomainInWatchHistoryMoviesMapper @Inject constructor()
    : Mapper<MovieInWatchHistoryLocalDto, MovieInWatchHistoryEntity> {
    override fun map(input: MovieInWatchHistoryLocalDto): MovieInWatchHistoryEntity {
        return MovieInWatchHistoryEntity(
            id = input.id,
            title = input.title,
            description = input.title,
            voteAverage = input.voteAverage,
            dateWatched = input.dateWatched,
            posterPath = input.posterPath,
            year = input.year
        )
    }
}