package com.elhady.movies.core.data.paging.movie

import com.elhady.movies.core.domain.model.movie.Movie
import com.elhady.movies.core.data.mapper.movie.DomainGenreMapper
import com.elhady.movies.core.data.mapper.movie.DomainTopRatedMoviesShowMoreMapper
import com.elhady.movies.core.database.dao.GenreDao
import com.elhady.movies.core.data.base.BasePagingSource
import com.elhady.movies.core.network.api.MovieApiService
import javax.inject.Inject

class TopRatedShowMorePagingSource @Inject constructor(
    service: MovieApiService,
    private val mapper: DomainTopRatedMoviesShowMoreMapper,
    private val domainGenreMapper: DomainGenreMapper,
    private val genreDao: GenreDao,
) : BasePagingSource<MovieApiService, Movie>(service) {

    override suspend fun fetchData(page: Int): List<Movie> {
        val response = service.getTopRatedMovies(page).body()?.results?.filterNotNull()
        val genreMovieMapper = domainGenreMapper.map(genreDao.getGenresMovies())
        return response?.map { mapper.map(it, genreMovieMapper) } ?: emptyList()
    }
}
