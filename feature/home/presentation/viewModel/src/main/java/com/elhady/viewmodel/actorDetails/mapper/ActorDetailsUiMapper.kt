package com.elhady.viewmodel.actorDetails.mapper

import com.elhady.mapper.Mapper
import com.elhady.viewmodel.actorDetails.ActorInfoUiState
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