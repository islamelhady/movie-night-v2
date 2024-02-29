package com.elhady.repository

import androidx.paging.Pager
import com.elhady.local.AppConfiguration
import com.elhady.local.database.daos.ActorDao
import com.elhady.local.database.entity.actor.ActorLocalDto
import com.elhady.local.mappers.actors.ActorsMapper
import com.elhady.remote.response.actor.MovieCreditsDto
import com.elhady.remote.response.actor.PersonDto
import com.elhady.remote.response.dto.MovieDto
import com.elhady.remote.serviece.MovieService
import com.elhady.repository.mediaDataSource.actors.ActorDataSource
import com.elhady.repository.mediaDataSource.actors.ActorMoviesDataSource
import com.elhady.repository.searchDataSource.ActorsSearchDataSource
import com.elhady.usecase.repository.ActorRepository
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
    override suspend fun getPopularPersons(): List<ActorLocalDto> {
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