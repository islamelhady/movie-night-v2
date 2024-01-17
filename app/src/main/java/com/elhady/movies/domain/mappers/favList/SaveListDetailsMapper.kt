package com.elhady.movies.domain.mappers.favList

import com.elhady.movies.data.remote.response.SavedListDto
import com.elhady.movies.domain.mappers.Mapper
import com.elhady.movies.domain.models.SaveListDetails
import com.elhady.movies.utilities.Constants
import javax.inject.Inject

class SaveListDetailsMapper @Inject constructor() : Mapper<SavedListDto, SaveListDetails> {
    override fun map(input: SavedListDto): SaveListDetails {
        return SaveListDetails(
            id = input.id ?: 0,
            mediaType = input.mediaType ?: "",
            title = input.originalTitle.toString(),
            releaseDate = input.releaseDate.toString(),
            voteAverage = input.voteAverage ?: 0.0,
            posterPath = Constants.IMAGE_PATH + input.backdropPath,
        )
    }
}