package com.elhady.movies.domain.mappers.movie

import com.elhady.movies.data.remote.response.movie.MovieDetailsDto
import com.elhady.movies.domain.mappers.Mapper
import com.elhady.movies.domain.models.MovieDetails
import com.elhady.movies.utilities.Constants
import com.elhady.movies.utilities.convertToDayMonthYearFormat
import javax.inject.Inject

class MovieDetailsDtoMapper @Inject constructor() : Mapper<MovieDetailsDto, MovieDetails> {
    override fun map(input: MovieDetailsDto): MovieDetails {
        return MovieDetails(
            id = input.id ?: 0,
            name = input.title ?: "",
            image = (Constants.IMAGE_PATH + input.backdropPath),
            releaseDate = input.releaseDate?.convertToDayMonthYearFormat() ?: "",
            overview = input.overview ?: "",
            voteAverage = input.voteAverage.toString().take(3),
            duration = input.runtime ?: 0,
            review = input.voteCount ?: 0,
            genres = input.genres?.map {
                it?.name
            }?.joinToString(",") ?: ""
        )
    }
}