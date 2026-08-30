package com.elhady.movies.core.data.mapper.movie

import com.elhady.movies.core.common.MediaType
import com.elhady.movies.core.data.BuildConfig
import com.elhady.movies.core.network.dto.tvshow.TvDto
import com.elhady.movies.core.domain.model.common.Genre
import com.elhady.movies.core.domain.model.movie.Movie
import javax.inject.Inject

class TvDtoMapper @Inject constructor() {
    fun map(input: TvDto, genres: List<Genre>, mediaType: MediaType = MediaType.TV_SHOW): Movie {
        return Movie(
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
