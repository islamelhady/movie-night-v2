package com.elhady.movies.feature.home.presentation.mappers

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.movie.MovieEntity
import com.elhady.movies.feature.home.presentation.UpComingMoviesUiState
import javax.inject.Inject

class UpComingUiMapper @Inject constructor() : Mapper<MovieEntity, UpComingMoviesUiState> {
    override fun map(input: MovieEntity): UpComingMoviesUiState {
        return UpComingMoviesUiState(
            id = input.id,
            imageUrl = input.imageUrl,
            title = input.title,
            genres = input.genreEntities.map { it.genreName },
            rate = input.rate
        )
    }
}
