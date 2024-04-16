package com.elhady.movies.presentation.viewmodel.home.mappers

import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.domain.entities.MovieEntity
import com.elhady.movies.presentation.viewmodel.home.TopRatedUiState
import javax.inject.Inject

class TopRatedUiMapper @Inject constructor()  : Mapper<MovieEntity, TopRatedUiState> {
    override fun map(input: MovieEntity): TopRatedUiState {
        return TopRatedUiState(
            input.id,
            input.imageUrl,
            input.rate
        )
    }
}