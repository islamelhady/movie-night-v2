package com.elhady.movies.feature.details.presentation.peopledetails.mapper

import com.elhady.movies.core.common.mapper.Mapper
import com.elhady.movies.core.domain.model.tvshow.TvShow
import com.elhady.movies.feature.details.presentation.peopledetails.PersonDetailsUiState
import javax.inject.Inject

class TvShowsByPeopleUiMapper @Inject constructor() :
    Mapper<TvShow, PersonDetailsUiState.PeopleMediaUiState> {
    override fun map(input: TvShow): PersonDetailsUiState.PeopleMediaUiState {
        return PersonDetailsUiState.PeopleMediaUiState(
            id = input.id,
            type = "tvShows",
            imageUrl = input.imageUrl,
            rate = input.rate
        )
    }
}
