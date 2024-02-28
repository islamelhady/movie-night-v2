package com.elhady.usecase.favList

import com.elhady.entities.SaveListDetailsEntity
import com.elhady.usecase.repository.MovieRepository
import javax.inject.Inject

class GetFavListDetailsUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val saveListDetailsMapper: SaveListDetailsMapper
) {
    suspend operator fun invoke(listID: Int): List<SaveListDetailsEntity> {
        return movieRepository.getSavedListDetails(listID)?.map {
            saveListDetailsMapper.map(it)
        } ?: throw Throwable("empty")
    }
}