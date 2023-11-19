package com.elhady.movies.domain.usecases.favList

import com.elhady.movies.data.repository.MovieRepository
import com.elhady.movies.utilities.checkIfExist
import javax.inject.Inject

class SaveMovieToFavListUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {

    suspend operator fun invoke(listID: Int, mediaId: Int): String {
        val result = movieRepository.getListDetails(listID)

        return if (result?.checkIfExist(mediaId) == true) {
            "Fail: this movie is already on the list"
        } else {
            TODO()
        }
    }

}