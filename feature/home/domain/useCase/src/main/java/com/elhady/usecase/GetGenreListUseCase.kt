package com.elhady.usecase

import com.elhady.entities.GenreEntity
import com.elhady.usecase.repository.MovieRepository
import com.elhady.usecase.repository.SeriesRepository
import javax.inject.Inject

class GetGenreListUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val seriesRepository: SeriesRepository,
    private val genreMapper: GenreMapper
) {
    suspend operator fun invoke(type: MediaType): List<GenreEntity> {
        val genre = when (type) {
            MediaType.MOVIES -> {
                movieRepository.getGenreMovies()?.map {
                    genreMapper.map(it)

                } ?: throw Throwable("not success")
            }

            MediaType.SERIES -> {
                seriesRepository.getGenreSeries()?.map {
                    genreMapper.map(it)
                } ?: throw Throwable("not success")
            }
        }

        return setGenre(genre)
    }

    private fun setGenre(genre: List<GenreEntity>): List<GenreEntity> {
        val genresList = mutableListOf<GenreEntity>()
        genresList.add(0, GenreEntity(FIRST_CATEGORY_ID, FIRST_CATEGORY_NAME))
        genresList.addAll(genre)
        return genresList.toList()

    }

    companion object{
        const val FIRST_CATEGORY_ID = 0
        const val FIRST_CATEGORY_NAME = "All"
    }

}
enum class MediaType(val value: String)  {
    MOVIES("movie"),
    SERIES("tv")
}