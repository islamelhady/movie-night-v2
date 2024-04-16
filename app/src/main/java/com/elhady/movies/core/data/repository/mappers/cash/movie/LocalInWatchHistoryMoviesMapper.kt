package com.elhady.movies.core.data.repository.mappers.cash.movie

import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.data.local.database.dto.movie.MovieInWatchHistoryLocalDto
import com.elhady.movies.core.domain.entities.MovieInWatchHistoryEntity
import javax.inject.Inject

class LocalInWatchHistoryMoviesMapper @Inject constructor()
    : Mapper<MovieInWatchHistoryEntity, MovieInWatchHistoryLocalDto> {
    override fun map(input: MovieInWatchHistoryEntity): MovieInWatchHistoryLocalDto {
        return MovieInWatchHistoryLocalDto(
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