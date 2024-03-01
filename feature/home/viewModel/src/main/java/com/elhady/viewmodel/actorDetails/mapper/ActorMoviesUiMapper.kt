package com.elhady.viewmodel.actorDetails.mapper

import com.elhady.mapper.Mapper
import com.elhady.viewmodel.actorDetails.ActorMoviesUiState
import javax.inject.Inject

class ActorMoviesUiMapper @Inject constructor() : Mapper<ActorMovies, ActorMoviesUiState> {
    override fun map(input: ActorMovies): ActorMoviesUiState {
        return ActorMoviesUiState(
            id = input.movieId,
            image = input.movieImage
        )
    }
}