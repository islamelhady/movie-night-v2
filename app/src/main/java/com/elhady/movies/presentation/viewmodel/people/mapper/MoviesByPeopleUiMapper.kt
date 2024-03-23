package com.elhady.movies.presentation.viewmodel.people.mapper

import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.domain.entities.MovieEntity
import com.elhady.movies.presentation.viewmodel.people.PersonDetailsUiState
import javax.inject.Inject

class MoviesByPeopleUiMapper @Inject constructor() :
    Mapper<MovieEntity, PersonDetailsUiState.PeopleMediaUiState> {
    override fun map(input: MovieEntity): PersonDetailsUiState.PeopleMediaUiState {
        return PersonDetailsUiState.PeopleMediaUiState(
            id = input.id,
            name = "movies",
            imageUrl = input.imageUrl,
            rate = input.rate
        )
    }
}