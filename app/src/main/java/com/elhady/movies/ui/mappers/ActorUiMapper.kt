package com.elhady.movies.ui.mappers

import com.elhady.movies.domain.mappers.Mapper
import com.elhady.movies.domain.models.Actor
import com.elhady.movies.ui.models.ActorUiState
import javax.inject.Inject

class ActorUiMapper @Inject constructor(): Mapper<Actor, ActorUiState> {
    override fun map(input: Actor): ActorUiState {
        return ActorUiState(
            id = input.id,
            name = input.name,
            imageUrl = input.imageUrl
        )
    }
}