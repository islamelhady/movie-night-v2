package com.elhady.movies.ui.actorDetails

import com.elhady.movies.domain.mappers.Mapper
import com.elhady.movies.domain.models.ActorDetails
import javax.inject.Inject

class ActorDetailsUiMapper @Inject constructor() : Mapper<ActorDetails, ActorDetailsUiState> {
    override fun map(input: ActorDetails): ActorDetailsUiState {
        return ActorDetailsUiState(
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