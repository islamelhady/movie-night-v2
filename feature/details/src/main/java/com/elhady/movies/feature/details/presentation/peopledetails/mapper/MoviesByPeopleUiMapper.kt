package com.elhady.movies.feature.details.presentation.peopledetails.mapper

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.movie.Movie
import com.elhady.movies.feature.details.presentation.peopledetails.PersonDetailsUiState
import javax.inject.Inject

class MoviesByPeopleUiMapper @Inject constructor() :
    Mapper<Movie, PersonDetailsUiState.PeopleMediaUiState> {
    override fun map(input: Movie): PersonDetailsUiState.PeopleMediaUiState {
        return PersonDetailsUiState.PeopleMediaUiState(
            id = input.id,
            type = "movies",
            imageUrl = input.imageUrl,
            rate = input.rate
        )
    }
}
