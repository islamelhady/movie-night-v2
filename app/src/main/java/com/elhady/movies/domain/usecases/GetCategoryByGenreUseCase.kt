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
import com.elhady.movies.utilities.Constants.FIRST_CATEGORY_ID
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
                if (genreId == FIRST_CATEGORY_ID) wrapPaging(movieRepository::getAllMovies, movieDtoMapper::map)
                else wrapPaging({ movieRepository.getMoviesByGenre(genreId) }, movieDtoMapper::map)
            }

            MediaType.SERIES -> {
                if (genreId == FIRST_CATEGORY_ID) wrapPaging(seriesRepository::getAllSeries, seriesDtoMapper::map)
                else wrapPaging({ seriesRepository.getSeriesByGenre(genreId) }, seriesDtoMapper::map)
            }
        }
    }



    private suspend fun <T : Any> wrapPaging(
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