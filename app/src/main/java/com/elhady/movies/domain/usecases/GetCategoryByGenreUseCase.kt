package com.elhady.movies.domain.usecases

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.elhady.movies.data.repository.MovieRepository
import com.elhady.movies.data.repository.SeriesRepository
import com.elhady.movies.domain.enums.MediaType
import com.elhady.movies.domain.mappers.movie.MovieDtoMapper
import com.elhady.movies.domain.mappers.series.SeriesDtoMapper
import com.elhady.movies.domain.models.Media
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCategoryByGenreUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val seriesRepository: SeriesRepository,
    private val movieDtoMapper: MovieDtoMapper,
    private val seriesDtoMapper: SeriesDtoMapper
) {
    suspend operator fun invoke(type: MediaType, genreId: Int): Flow<PagingData<Media>> {
        return when (type) {
            MediaType.MOVIES -> {
                wrapPaging({ movieRepository.getMoviesByGenre(genreId) }, movieDtoMapper::map)
            }

            MediaType.SERIES -> {
                wrapPaging({ seriesRepository.getSeriesByGenre(genreId) }, seriesDtoMapper::map)
            }
        }
    }


    suspend fun <T : Any> wrapPaging(
        response: suspend () -> Pager<Int, T>,
        mapper: (T) -> Media
    ): Flow<PagingData<Media>> {
        return response().flow.map { pagingData ->
            pagingData.map {
                mapper(it)
            }
        }
    }


}