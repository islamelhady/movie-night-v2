package com.elhady.movies.ui.actorDetails.mapper

import com.elhady.movies.domain.mappers.Mapper
import com.elhady.movies.domain.models.ActorMovies
import com.elhady.movies.ui.actorDetails.ActorMoviesUiState
import javax.inject.Inject

class ActorMoviesUiMapper @Inject constructor() : Mapper<ActorMovies, ActorMoviesUiState> {
    override fun map(input: ActorMovies): ActorMoviesUiState {
        return ActorMoviesUiState(
            id = input.movieId,
            image = input.movieImage
        )
    }
}