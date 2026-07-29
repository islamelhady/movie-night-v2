package com.elhady.movies.core.data.mapper.people

import com.elhady.movies.core.data.BuildConfig
import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.network.dto.people.CastItem
import com.elhady.movies.core.domain.model.movie.Movie
import javax.inject.Inject

class DomainMoviesByPeopleMapper @Inject constructor() : Mapper<CastItem, Movie> {

    override fun map(input: List<CastItem>): List<Movie> {
        return input.map(::map)
    }

    override fun map(input: CastItem): Movie {
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
