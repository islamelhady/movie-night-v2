package com.elhady.movies.feature.details.presentation.peopledetails.mapper

import com.elhady.movies.core.common.Mapper
import com.elhady.movies.core.domain.model.tvshow.TvShow
import com.elhady.movies.feature.details.presentation.peopledetails.PeopleDetailsUiState
import javax.inject.Inject

class TvShowsByPeopleUiMapper @Inject constructor() :
    Mapper<TvShow, PeopleDetailsUiState.PeopleMediaUiState> {
    override fun map(input: TvShow): PeopleDetailsUiState.PeopleMediaUiState {
        return PeopleDetailsUiState.PeopleMediaUiState(
            id = input.id,
            type = PeopleDetailsUiState.MediaType.TV_SHOW,
            imageUrl = input.imageUrl,
            rate = input.rate
        )
    }
}
