package com.elhady.viewmodel.mappers

import com.elhady.mapper.Mapper
import com.elhady.viewmodel.models.ActorUiState
import javax.inject.Inject

class ActorUiMapper @Inject constructor(): Mapper<Actor, ActorUiState> {
    override fun map(input: Actor): ActorUiState {
        return ActorUiState(
            id = input.id,
            name = input.name,
            imageUrl = input.imageUrl,
            characterName = input.characterName
        )
    }
}