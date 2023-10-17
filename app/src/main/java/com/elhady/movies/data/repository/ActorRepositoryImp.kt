package com.elhady.movies.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.PrimaryKey
import com.elhady.movies.data.Constant
import com.elhady.movies.data.local.AppConfiguration
import com.elhady.movies.data.local.database.daos.ActorDao
import com.elhady.movies.data.local.database.entity.actor.ActorEntity
import com.elhady.movies.data.local.mappers.actors.ActorsMapper
import com.elhady.movies.data.remote.response.PersonDto
import com.elhady.movies.data.remote.service.MovieService
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject

class ActorRepositoryImp @Inject constructor(
    private val service: MovieService,
    private val actorsMapper: ActorsMapper,
    private val actorDao: ActorDao,
    private val appConfiguration: AppConfiguration,
    private val actorDataSource: ActorDataSource
) : BaseRepository(), ActorRepository {

    /**
     *  Trending Actors
     */
    override suspend fun getTrendingActors(): Flow<List<ActorEntity>> {
        refreshOneTimePerDay(
            appConfiguration.getRequestDate(Constant.ACTOR_TRENDING_REQUEST_DATE_KEY),
            ::refreshTrendingActors
        )
        return actorDao.getActors()
    }

    private suspend fun refreshTrendingActors(currentDate: Date) {
        wrap(
            { service.getTrendingPerson() },
            { list ->
                list?.map {
                    actorsMapper.map(it)
                }
            },
            {
                actorDao.deleteActors()
                actorDao.insertActors(it)
                appConfiguration.saveRequestDate(
                    Constant.ACTOR_TRENDING_REQUEST_DATE_KEY,
                    currentDate.time
                )
            }
        )
    }

    /**
     *  Paging All Actors
     */
    override suspend fun getAllActors(): Pager<Int, PersonDto> {
        return Pager(config = pagingConfig) { actorDataSource }
    }


}