package com.elhady.movies.domain.usecases.favList

import com.elhady.movies.data.repository.MovieRepository
import com.elhady.movies.domain.models.SaveListDetails
import javax.inject.Inject

class GetFavListDetailsUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(listID: Int): List<SaveListDetails> {

    }
}