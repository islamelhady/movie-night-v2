package com.elhady.movies.ui.mappers

import com.elhady.movies.domain.mappers.Mapper
import com.elhady.movies.domain.models.MovieDetails
import com.elhady.movies.ui.movieDetails.MovieDetailsUiState
import javax.inject.Inject

class MovieDetailsUiMapper @Inject constructor() : Mapper<MovieDetails, MovieDetailsUiState> {


    override fun map(input: MovieDetails): MovieDetailsUiState {

        val duration = formatMovieDuration(input.duration)

        return MovieDetailsUiState(
            id = input.id,
            name = input.name,
            image = input.image,
            overview = input.overview,
            releaseDate = input.releaseDate,
            genres = input.genres,
            hours = duration.hours,
            minutes = duration.minutes,
            specialNumber = input.duration,
            review = input.review,
            voteAverage = input.voteAverage,
        )
    }

    data class MovieDuration(val hours: Int, val minutes: Int)

    private fun formatMovieDuration(duration: Int): MovieDuration {
        return MovieDuration(hours = duration.div(60), minutes = duration.rem(60))
    }
}