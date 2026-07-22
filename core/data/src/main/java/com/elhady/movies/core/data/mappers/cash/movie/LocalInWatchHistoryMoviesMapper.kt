package com.elhady.movies.core.data.mappers.cash.movie

import com.elhady.movies.core.common.domain.entities.MovieInWatchHistoryEntity
import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.database.dto.movie.MovieInWatchHistoryLocalDto
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
