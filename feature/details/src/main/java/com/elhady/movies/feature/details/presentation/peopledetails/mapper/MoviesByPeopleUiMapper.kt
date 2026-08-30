package com.elhady.movies.feature.details.presentation.peopledetails.mapper

import com.elhady.movies.core.common.Mapper
import com.elhady.movies.core.domain.model.movie.Movie
import com.elhady.movies.feature.details.presentation.peopledetails.PeopleDetailsUiState
import javax.inject.Inject

class MoviesByPeopleUiMapper @Inject constructor() :
    Mapper<Movie, PeopleDetailsUiState.PeopleMediaUiState> {
    override fun map(input: Movie): PeopleDetailsUiState.PeopleMediaUiState {
        return PeopleDetailsUiState.PeopleMediaUiState(
            id = input.id,
            type = PeopleDetailsUiState.MediaType.MOVIE,
            imageUrl = input.imageUrl,
            rate = input.rate
        )
    }
}
