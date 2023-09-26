package com.elhady.movies.domain.mappers

import com.elhady.movies.data.remote.response.MovieDto
import com.elhady.movies.domain.models.PopularMovie
import javax.inject.Inject

class PopularMovieMapper @Inject constructor() : Mapper<MovieDto, PopularMovie> {
    override fun map(input: MovieDto): PopularMovie {
        return PopularMovie(
            movieId = input.id,
            imageUrl = input.posterPath,
            title = input.title,
            movieRate = input.voteAverage,
//            genre = input.genreIds
        )
    }
}