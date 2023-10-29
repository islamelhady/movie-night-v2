package com.elhady.movies.domain.mappers.movie

import com.elhady.movies.data.remote.response.movie.MovieDetailsDto
import com.elhady.movies.domain.mappers.Mapper
import com.elhady.movies.domain.models.MovieDetails
import com.elhady.movies.utilities.Constants
import javax.inject.Inject

class MovieDetailsDtoMapper @Inject constructor() : Mapper<MovieDetailsDto, MovieDetails> {
    override fun map(input: MovieDetailsDto): MovieDetails {
        return MovieDetails(
            id = input.id ?: 0,
            name = input.title ?: "",
            image = (Constants.IMAGE_PATH + input.backdropPath)
        )
    }
}