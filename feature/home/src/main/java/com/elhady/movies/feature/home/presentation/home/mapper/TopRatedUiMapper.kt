package com.elhady.movies.feature.home.presentation.home.mapper

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.movie.Movie
import com.elhady.movies.feature.home.presentation.home.TopRatedUiState
import javax.inject.Inject

class TopRatedUiMapper @Inject constructor()  : Mapper<Movie, TopRatedUiState> {
    override fun map(input: Movie): TopRatedUiState {
        return TopRatedUiState(
            input.id,
            input.imageUrl,
            input.rate
        )
    }
}
