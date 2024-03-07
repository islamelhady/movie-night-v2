package com.elhady.viewmodel.mappers

import com.elhady.mapper.Mapper
import com.elhady.viewmodel.movieDetails.HeaderDetailsUiState
import javax.inject.Inject

class MovieDetailsUiMapper @Inject constructor() : Mapper<MovieDetails, HeaderDetailsUiState> {


    override fun map(input: MovieDetails): HeaderDetailsUiState {

        val duration = formatMovieDuration(input.duration)

        return HeaderDetailsUiState(
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