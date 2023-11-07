package com.elhady.movies.data.repository.mediaDataSource.actors

import com.elhady.movies.data.remote.response.movie.MovieDto
import com.elhady.movies.data.remote.service.MovieService
import com.elhady.movies.data.repository.mediaDataSource.BasePagingSource
import javax.inject.Inject
import kotlin.properties.Delegates

class ActorMoviesDataSource @Inject constructor (private val service: MovieService) :
    BasePagingSource<MovieDto>() {

    private var actorID by Delegates.notNull<Int>()

    fun setActorMovieId(actor: Int) {
        actorID = actor
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieDto> {
        val response = service.getPersonMovies(actorID = actorID)

        return try {
            LoadResult.Page(
                data = response.body()?.cast ?: emptyList(),
                prevKey = null,
                nextKey = null
            )
        } catch (error: Throwable) {
            LoadResult.Error(error)
        }
    }
}