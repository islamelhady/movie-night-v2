package com.elhady.movies.feature.details.presentation.peopledetails.mapper

import com.elhady.movies.core.common.Mapper
import com.elhady.movies.core.domain.model.people.PeopleDetails
import com.elhady.movies.feature.details.presentation.peopledetails.PeopleDetailsUiState
import javax.inject.Inject

class PeopleDataUiMapper @Inject constructor() :
    Mapper<PeopleDetails, PeopleDetailsUiState.PersonInfoUiState> {
    override fun map(input: PeopleDetails): PeopleDetailsUiState.PersonInfoUiState {
        return PeopleDetailsUiState.PersonInfoUiState(
            id = input.id,
            name = input.name,
            imageUrl = input.imageUrl,
            placeOfBirth = input.placeOfBirth,
            gender = input.gender,
            acting = input.acting,
            numMovies = input.numMovies,
            biography = input.biography
        )
    }
}
