package com.elhady.movies.domain.usecases

import com.elhady.movies.data.repository.MovieRepository
import com.elhady.movies.data.repository.SeriesRepository
import com.elhady.movies.domain.enums.MediaType
import com.elhady.movies.domain.mappers.TrailerMapper
import com.elhady.movies.domain.models.Trailer
import javax.inject.Inject

class GetTrailerUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val seriesRepository: SeriesRepository,
    private val trailerMapper: TrailerMapper
) {
    suspend operator fun invoke(mediaType: MediaType, mediaId: Int): Trailer {
        val response = when (mediaType) {
            MediaType.MOVIES -> {
                movieRepository.getMovieTrailer(mediaId)
            }

            MediaType.SERIES -> {
                seriesRepository.getSeriesTrailer(mediaId)
            }
        }
        return response?.let {
            trailerMapper.map(it)
        } ?: throw Throwable("error")
    }

}