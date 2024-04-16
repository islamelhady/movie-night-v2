package com.elhady.movies.presentation.viewmodel.peopledetails.mapper

import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.domain.entities.PeopleDetailsEntity
import com.elhady.movies.presentation.viewmodel.peopledetails.PersonDetailsUiState
import javax.inject.Inject

class PeopleDataUiMapper @Inject constructor() :
    Mapper<PeopleDetailsEntity, PersonDetailsUiState.PersonInfoUiState> {
    override fun map(input: PeopleDetailsEntity): PersonDetailsUiState.PersonInfoUiState {
        return PersonDetailsUiState.PersonInfoUiState(
            id = input.id,
            name = input.name,
            imageUrl = input.imageUrl,
            placeOfBirth = input.placeOfBirth,
            gender = input.gender,
            acting = input.acting,
            numMovies = input.num_movies,
            biography = input.biography
        )
    }
}