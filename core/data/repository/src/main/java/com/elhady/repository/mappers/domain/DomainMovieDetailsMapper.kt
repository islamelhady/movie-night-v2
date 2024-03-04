package com.elhady.repository.mappers.domain

import com.elhady.movies.domain.mappers.Mapper
import javax.inject.Inject

class DomainMovieDetailsMapper @Inject constructor() : Mapper<Movdet, MovieDetails> {
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
            }?.joinToString(" • ") ?: ""
        )
    }
}