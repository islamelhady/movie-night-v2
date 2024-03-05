package com.elhady.viewmodel.home.mappers

import com.elhady.entities.ActorEntity
import com.elhady.mapper.Mapper
import com.elhady.viewmodel.home.homeUiState.PopularActorUiState
import javax.inject.Inject

class PopularActorUiMapper @Inject constructor(): Mapper<ActorEntity, PopularActorUiState> {
    override fun map(input: ActorEntity): PopularActorUiState {
        return PopularActorUiState(
            id = input.id,
            name = input.characterName,
            profilePath = input.imageUrl,
        )
    }
}