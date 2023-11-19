package com.elhady.movies.domain.usecases.favList

import com.elhady.movies.data.repository.MovieRepository
import com.elhady.movies.domain.mappers.favList.SaveListDetailsMapper
import com.elhady.movies.domain.models.SaveListDetails
import javax.inject.Inject

class GetFavListDetailsUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val saveListDetailsMapper: SaveListDetailsMapper
) {
    suspend operator fun invoke(listID: Int): List<SaveListDetails> {
        return movieRepository.getSavedListDetails(listID)?.map {
            saveListDetailsMapper.map(it)
        } ?: throw Throwable("empty")
    }
}