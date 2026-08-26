package com.elhady.movies.feature.details.presentation.moviedetails.mapper

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.movie.MovieDetails
import com.elhady.movies.core.domain.model.movie.MovieVideo
import com.elhady.movies.feature.details.presentation.moviedetails.UpperUiState
import javax.inject.Inject

class UpperUiMapper @Inject constructor() :
    Mapper<MovieDetails, UpperUiState> {
    override fun map(input: MovieDetails): UpperUiState {
        return UpperUiState(
            id = input.id,
            backdropPath = input.backdropPath,
            genres = input.genres,
            title = input.title,
            overview = input.overview,
            voteAverage = input.voteAverage.toFloat().div(2f),
            videoKey = getTheFirstVideoKeyInList(input.videos.results),
        )
    }

    private fun getTheFirstVideoKeyInList(results: List<MovieVideo>): String {
        return if (results.isNotEmpty()) results.map { it.key }.first() else ""
    }

}
