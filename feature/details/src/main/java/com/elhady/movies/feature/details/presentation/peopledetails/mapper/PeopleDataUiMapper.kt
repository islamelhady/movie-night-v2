package com.elhady.movies.feature.details.presentation.peopledetails.mapper

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.people.PeopleDetails
import com.elhady.movies.feature.details.presentation.peopledetails.PersonDetailsUiState
import javax.inject.Inject

class PeopleDataUiMapper @Inject constructor() :
    Mapper<PeopleDetails, PersonDetailsUiState.PersonInfoUiState> {
    override fun map(input: PeopleDetails): PersonDetailsUiState.PersonInfoUiState {
        return PersonDetailsUiState.PersonInfoUiState(
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
