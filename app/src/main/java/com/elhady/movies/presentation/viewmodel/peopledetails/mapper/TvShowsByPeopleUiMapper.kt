package com.elhady.movies.presentation.viewmodel.peopledetails.mapper

import com.elhady.movies.core.mapper.Mapper
import com.elhady.movies.core.domain.entities.TvShowEntity
import com.elhady.movies.presentation.viewmodel.peopledetails.PersonDetailsUiState
import javax.inject.Inject

class TvShowsByPeopleUiMapper @Inject constructor() :
    Mapper<TvShowEntity, PersonDetailsUiState.PeopleMediaUiState> {
    override fun map(input: TvShowEntity): PersonDetailsUiState.PeopleMediaUiState {
        return PersonDetailsUiState.PeopleMediaUiState(
            id = input.id,
            type = "tvShows",
            imageUrl = input.imageUrl,
            rate = input.rate
        )
    }
}