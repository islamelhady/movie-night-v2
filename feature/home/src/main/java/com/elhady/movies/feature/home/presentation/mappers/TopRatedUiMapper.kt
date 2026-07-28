package com.elhady.movies.feature.home.presentation.mappers

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.movie.MovieEntity
import com.elhady.movies.feature.home.presentation.TopRatedUiState
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
