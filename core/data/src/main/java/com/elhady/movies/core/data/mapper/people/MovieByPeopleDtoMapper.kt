package com.elhady.movies.core.data.mapper.people

import com.elhady.movies.core.data.BuildConfig
import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.network.dto.people.CastDto
import com.elhady.movies.core.domain.model.movie.Movie
import javax.inject.Inject

class MovieByPeopleDtoMapper @Inject constructor() : Mapper<CastDto, Movie> {

    override fun map(input: CastDto): Movie {
        return Movie(
            id = input.id ?: 0,
            title = input.title ?: "",
            imageUrl = BuildConfig.IMAGE_BASE_PATH + input.posterPath,
            rate = input.voteAverage ?: 0.0,
            year = input.releaseDate ?: "",
            genreEntities = emptyList()
        )
    }
}
