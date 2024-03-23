package com.elhady.movies.presentation.viewmodel.people.mapper

import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.domain.entities.PeopleDetailsEntity
import com.elhady.movies.presentation.viewmodel.people.PersonDetailsUiState
import javax.inject.Inject

class PeopleDataUiMapper @Inject constructor() :
    Mapper<PeopleDetailsEntity, PersonDetailsUiState.PersonInfoUiState> {
    override fun map(input: PeopleDetailsEntity): PersonDetailsUiState.PersonInfoUiState {
        return PersonDetailsUiState.PersonInfoUiState(
            input.id,
            input.name,
            imageUrl = input.imageUrl,
            input.placeOfBirth,
            input.gender,
            acting = input.acting,
            numMovies = input.num_movies,
            input.biography,

            )
    }
}