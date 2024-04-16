package com.elhady.movies.presentation.viewmodel.home.mappers

import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.domain.entities.MovieEntity
import com.elhady.movies.presentation.viewmodel.home.UpComingMoviesUiState
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