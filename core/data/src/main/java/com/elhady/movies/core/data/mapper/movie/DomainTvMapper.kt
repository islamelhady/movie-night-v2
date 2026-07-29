package com.elhady.movies.core.data.mapper.movie

import com.elhady.movies.core.data.BuildConfig
import com.elhady.movies.core.network.dto.tvshow.TvDto
import com.elhady.movies.core.domain.model.common.GenreEntity
import com.elhady.movies.core.domain.model.movie.MovieEntity
import javax.inject.Inject

class DomainTvMapper @Inject constructor() {
    fun map(input: TvDto, genres: List<GenreEntity>, mediaType:String="tv"): MovieEntity {
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
