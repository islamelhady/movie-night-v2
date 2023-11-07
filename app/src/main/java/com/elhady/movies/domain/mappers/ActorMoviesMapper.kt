package com.elhady.movies.domain.mappers

import com.elhady.movies.data.remote.response.movie.MovieDto
import com.elhady.movies.domain.models.ActorMovies
import com.elhady.movies.utilities.Constants
import javax.inject.Inject

class ActorMoviesMapper @Inject constructor() : Mapper<MovieDto, ActorMovies> {
    override fun map(input: MovieDto): ActorMovies {
        return ActorMovies(
            movieId = input.id ?: 0,
            movieImage = (Constants.IMAGE_PATH + input.posterPath),
        )
    }
}