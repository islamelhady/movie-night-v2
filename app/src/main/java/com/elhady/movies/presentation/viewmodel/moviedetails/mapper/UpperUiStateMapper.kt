package com.elhady.movies.presentation.viewmodel.moviedetails.mapper

import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.domain.entities.moviedetails.MovieDetailsEntity
import com.elhady.movies.core.domain.entities.moviedetails.MovieVideoEntity
import com.elhady.movies.presentation.viewmodel.moviedetails.UpperUiState
import javax.inject.Inject

class UpperUiStateMapper @Inject constructor() :
    Mapper<MovieDetailsEntity, UpperUiState> {
    override fun map(input: MovieDetailsEntity): UpperUiState {
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

    private fun getTheFirstVideoKeyInList(results: List<MovieVideoEntity>): String {
        return if (results.isNotEmpty()) results.map { it.key }.first() else ""
    }

}