package com.elhady.movies.ui.actorDetails.mapper

import com.elhady.movies.domain.mappers.Mapper
import com.elhady.movies.domain.models.ActorDetails
import com.elhady.movies.ui.actorDetails.ActorInfoUiState
import javax.inject.Inject

class ActorDetailsUiMapper @Inject constructor() : Mapper<ActorDetails, ActorInfoUiState> {
    override fun map(input: ActorDetails): ActorInfoUiState {
        return ActorInfoUiState(
            id = input.id,
            name = input.name,
            image = input.image,
            biography = input.biography,
            birthday = input.birthday,
            placeOfBirth = input.placeOfBirth,
            gender = input.gender,
            knownForDepartment = input.knownForDepartment
        )
    }
}