package com.elhady.movies.data.repository

import androidx.paging.Pager
import com.elhady.movies.data.Constant
import com.elhady.movies.data.local.AppConfiguration
import com.elhady.movies.data.local.database.daos.ActorDao
import com.elhady.movies.data.local.database.entity.actor.ActorEntity
import com.elhady.movies.data.local.mappers.actors.ActorsMapper
import com.elhady.movies.data.remote.response.movie.MovieDto
import com.elhady.movies.data.remote.response.actor.PersonDto
import com.elhady.movies.data.remote.response.actor.MovieCreditsDto
import com.elhady.movies.data.remote.service.MovieService
import com.elhady.movies.data.repository.mediaDataSource.actors.ActorDataSource
import com.elhady.movies.data.repository.mediaDataSource.actors.ActorMoviesDataSource
import com.elhady.movies.data.repository.searchDataSource.ActorsSearchDataSource
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject

class ActorRepositoryImp @Inject constructor(
    private val service: MovieService,
    private val actorsMapper: ActorsMapper,
    private val actorDao: ActorDao,
    private val appConfiguration: AppConfiguration,
    private val actorDataSource: ActorDataSource,
    private val actorMoviesDataSource: ActorMoviesDataSource,
    private val actorsSearchDataSource: ActorsSearchDataSource
) : BaseRepository(), ActorRepository {

    /**
     *  Popular Persons
     */
    override suspend fun getPopularPersons(): List<ActorEntity> {
        refreshOneTimePerDay(
            appConfiguration.getRequestDate(Constant.POPULAR_PERSON_REQUEST_DATE_KEY),
            ::refreshPopularPersons
        )
        return actorDao.getActors()
    }

    private suspend fun refreshPopularPersons(currentDate: Date) {
        wrap(
            { service.getPopularPerson() },
            { list ->
                list?.map {
                    actorsMapper.map(it)
                }
            },
            {
                actorDao.deleteActors()
                actorDao.insertActors(it)
                appConfiguration.saveRequestDate(
                    Constant.POPULAR_PERSON_REQUEST_DATE_KEY,
                    currentDate.time
                )
            }
        )
    }

    /**
     *  Paging All Persons
     */
    override fun getAllPopularPersons(): Pager<Int, PersonDto> {
        return Pager(config = pagingConfig, pagingSourceFactory = { actorDataSource })
    }

    /**
     *  Persons Details
     */

    override suspend fun getPersonsDetails(actorID: Int): PersonDto? {
        return service.getPersonsDetails(actorID = actorID).body()
    }

    /**
     *  Movie Credits
     */
    override suspend fun getPersonMovies(actorID: Int): MovieCreditsDto? {
        return service.getPersonMovies(actorID = actorID).body()
    }

    /**
     *  Paging All Actor Movies Credits
     */
    override suspend fun getAllActorMovies(actorID: Int): Pager<Int, MovieDto> {
        val actorDataSource = actorMoviesDataSource
        actorDataSource.setActorMovieId(actorID)
        return Pager(config = pagingConfig, pagingSourceFactory = { actorDataSource })
    }

    /**
     * Search for Actors
     */
    override suspend fun searchForActors(query: String): Pager<Int, PersonDto> {
        val dataSource = actorsSearchDataSource
        dataSource.setQuery(query)
        return Pager(config = pagingConfig, pagingSourceFactory = { dataSource })
    }


}