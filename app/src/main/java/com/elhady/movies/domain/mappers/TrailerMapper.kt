package com.elhady.movies.domain.mappers

import com.elhady.movies.data.remote.response.video.VideoDto
import com.elhady.movies.domain.models.Trailer
import com.elhady.movies.utilities.getKey
import javax.inject.Inject

class TrailerMapper @Inject constructor() : Mapper<VideoDto, Trailer> {
    override fun map(input: VideoDto): Trailer {
        return Trailer(videoKey = input.results?.getKey() ?: "")
    }
}


