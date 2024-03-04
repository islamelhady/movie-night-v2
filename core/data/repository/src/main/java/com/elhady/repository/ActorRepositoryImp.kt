package com.elhady.repository

import androidx.paging.Pager
import com.elhady.entities.ActorEntity
import com.elhady.local.AppConfiguration
import com.elhady.local.database.dao.ActorDao
import com.elhady.remote.response.actor.MovieCreditsDto
import com.elhady.remote.response.actor.PersonDto
import com.elhady.remote.response.dto.MovieRemoteDto
import com.elhady.remote.serviece.MovieService
import com.elhady.repository.mappers.cash.actor.LocalPopularActorsMapper
import com.elhady.repository.mappers.domain.DomainPopularActorMapper
import com.elhady.repository.mediaDataSource.actors.ActorDataSource
import com.elhady.repository.mediaDataSource.actors.ActorMoviesDataSource
import com.elhady.repository.searchDataSource.ActorsSearchDataSource
import com.elhady.usecase.repository.ActorRepository
import java.util.Random
import javax.inject.Inject

class ActorRepositoryImp @Inject constructor(
    private val service: MovieService,
    private val random: Random,
    private val domainPopularActorMapper: DomainPopularActorMapper,
    private val localPopularActorsMapper: LocalPopularActorsMapper,
    private val actorDao: ActorDao,
    private val appConfiguration: AppConfiguration,
    private val actorDataSource: ActorDataSource,
    private val actorMoviesDataSource: ActorMoviesDataSource,
    private val actorsSearchDataSource: ActorsSearchDataSource
) : BaseRepository(), ActorRepository {

    /**
     *  Popular Actor
     */
    override suspend fun getPopularActorFromDatabase(): List<ActorEntity> {
        return domainPopularActorMapper.map(actorDao.getActors())
    }

    override suspend fun refreshPopularActor() {
        refreshWrapper(
            { service.getPopularPerson(page = random.nextInt(20) + 1) },
            localPopularActorsMapper::map,
            actorDao::deleteActors,
            actorDao::insertActors
        )
    }

    /**
     *  Paging All Persons
     */
//    override fun getAllPopularPersons(): Pager<Int, PersonDto> {
//        return Pager(config = pagingConfig, pagingSourceFactory = { actorDataSource })
//    }

    /**
     *  Persons Details
     */

//    override suspend fun getPersonsDetails(actorID: Int): PersonDto? {
//        return service.getPersonsDetails(actorID = actorID).body()
//    }

    /**
     *  Movie Credits
     */
//    override suspend fun getPersonMovies(actorID: Int): MovieCreditsDto? {
//        return service.getPersonMovies(actorID = actorID).body()
//    }

    /**
     *  Paging All Actor Movies Credits
     */
//    override suspend fun getAllActorMovies(actorID: Int): Pager<Int, MovieRemoteDto> {
//        val actorDataSource = actorMoviesDataSource
//        actorDataSource.setActorMovieId(actorID)
//        return Pager(config = pagingConfig, pagingSourceFactory = { actorDataSource })
//    }

    /**
     * Search for Actors
     */
//    override suspend fun searchForActors(query: String): Pager<Int, PersonDto> {
//        val dataSource = actorsSearchDataSource
//        dataSource.setQuery(query)
//        return Pager(config = pagingConfig, pagingSourceFactory = { dataSource })
//    }


}