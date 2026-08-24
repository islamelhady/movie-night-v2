package com.elhady.movies.feature.home.presentation.home.mapper

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.movie.Movie
import com.elhady.movies.feature.home.presentation.home.TopRatedMovieUiState
import javax.inject.Inject

class TopRatedUiMapper @Inject constructor()  : Mapper<Movie, TopRatedMovieUiState> {
    override fun map(input: Movie): TopRatedMovieUiState {
        return TopRatedMovieUiState(
            id = input.id,
            imageUrl = input.imageUrl,
            rate = input.rate
        )
    }
}
