package com.elhady.usecase

import com.elhady.entities.TrailerEntity
import com.elhady.usecase.repository.MovieRepository
import com.elhady.usecase.repository.SeriesRepository
import javax.inject.Inject

class GetTrailerUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val seriesRepository: SeriesRepository,
    private val trailerMapper: TrailerMapper
) {
    suspend operator fun invoke(mediaType: MediaType, mediaId: Int): TrailerEntity {
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