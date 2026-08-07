package com.elhady.movies.core.data.paging.movie

import com.elhady.movies.core.domain.model.movie.Movie
import com.elhady.movies.core.data.mapper.movie.GenreEntityMapper
import com.elhady.movies.core.data.mapper.movie.TrendingMovieShowMoreDtoMapper
import com.elhady.movies.core.database.dao.genre.GenreMovieDao
import com.elhady.movies.core.data.base.BasePagingSource
import com.elhady.movies.core.network.api.MovieApiService
import javax.inject.Inject

class TrendingShowMorePagingSource @Inject constructor(
    service: MovieApiService,
    private val mapper: TrendingMovieShowMoreDtoMapper,
    private val domainGenreMapper: GenreEntityMapper,
    private val genreMovieDao: GenreMovieDao,
) : BasePagingSource<MovieApiService, Movie>(service) {
    override suspend fun fetchData(page: Int): List<Movie> {
        val response = service.getTrendingMovies(page = page).body()?.results?.filterNotNull()
        val genreMovieMapper = domainGenreMapper.map(genreMovieDao.getGenresMovies())
        return response?.map { mapper.map(it, genreMovieMapper) } ?: emptyList()
    }
}
