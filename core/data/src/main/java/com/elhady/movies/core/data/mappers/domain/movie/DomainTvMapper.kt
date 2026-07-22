package com.elhady.movies.core.data.mappers.domain.movie

import com.elhady.movies.core.data.BuildConfig
import com.elhady.movies.core.network.model.response.dto.TvRemoteDto
import com.elhady.movies.core.common.domain.entities.GenreEntity
import com.elhady.movies.core.common.domain.entities.MovieEntity
import javax.inject.Inject

class DomainTvMapper @Inject constructor() {
    fun map(input: TvRemoteDto, genres: List<GenreEntity>, mediaType:String="tv"): MovieEntity {
        return MovieEntity(
            id = input.id ?: 0,
            title = input.name ?: "",
            rate = input.voteAverage ?: 0.0,
            imageUrl = BuildConfig.IMAGE_BASE_PATH + input.posterPath,
            year = input.firstAirDate ?: "",
            genreEntities = genres,
            mediaType = mediaType,
        )
    }
}
