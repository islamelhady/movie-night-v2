package com.elhady.movies.core.data.repository.mappers.domain.movie

import com.elhady.movies.core.data.remote.response.dto.TvRemoteDto
import com.elhady.movies.core.data.repository.Constants.IMAGE_BASE_PATH
import com.elhady.movies.core.domain.entities.GenreEntity
import com.elhady.movies.core.domain.entities.MovieEntity
import javax.inject.Inject

class DomainTvMapper @Inject constructor() {
    fun map(input: TvRemoteDto, genres: List<GenreEntity>, mediaType:String="tv"): MovieEntity {
        return MovieEntity(
            id = input.id ?: 0,
            title = input.name ?: "",
            rate = input.voteAverage ?: 0.0,
            imageUrl = IMAGE_BASE_PATH + input.posterPath,
            year = input.firstAirDate ?: "",
            genreEntities = genres,
            mediaType = mediaType,
        )
    }
}