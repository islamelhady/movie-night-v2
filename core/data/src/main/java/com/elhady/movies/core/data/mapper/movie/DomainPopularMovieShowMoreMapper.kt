package com.elhady.movies.core.data.mapper.movie

import com.elhady.movies.core.data.BuildConfig
import com.elhady.movies.core.network.model.response.dto.MovieRemoteDto
import com.elhady.movies.core.domain.model.common.GenreEntity
import com.elhady.movies.core.domain.model.movie.MovieEntity
import javax.inject.Inject

class DomainPopularMovieShowMoreMapper @Inject constructor() {

    fun map(input: MovieRemoteDto, genreEntities: List<GenreEntity>): MovieEntity {
        return MovieEntity(
            id = input.id ?: 0,
            title = input.title ?: "",
            imageUrl = BuildConfig.IMAGE_BASE_PATH + input.posterPath,
            year = input.releaseDate ?: "",
            genreEntities = genreEntities.filter {
                it.genreID in (input.genreIds?.filterNotNull() ?: emptyList())
            },
            rate = input.voteAverage ?: 0.0
        )
    }
}
