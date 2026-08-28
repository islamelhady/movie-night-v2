package com.elhady.movies.core.data.repository

import androidx.room.withTransaction
import com.elhady.movies.core.data.mapper.people.PopularPeopleDtoToEntityMapper
import com.elhady.movies.core.data.mapper.people.MovieByPeopleDtoMapper
import com.elhady.movies.core.data.mapper.people.PeopleDetailsDtoMapper
import com.elhady.movies.core.data.mapper.people.PopularPeopleEntityMapper
import com.elhady.movies.core.data.mapper.people.PeopleDtoMapper
import com.elhady.movies.core.data.mapper.people.TvShowCastDtoMapper
import com.elhady.movies.core.database.dao.PeopleDao
import com.elhady.movies.core.database.db.MovieDatabase
import com.elhady.movies.core.domain.model.movie.Movie
import com.elhady.movies.core.domain.model.people.PeopleDetails
import com.elhady.movies.core.domain.model.people.People
import com.elhady.movies.core.domain.model.tvshow.TvShow
import com.elhady.movies.core.domain.repository.PeopleRepository
import com.elhady.movies.core.network.api.PeopleApiService
import com.elhady.movies.core.network.exception.SafeApiCaller
import java.util.Random
import javax.inject.Inject

class PeopleRepositoryImpl @Inject constructor(
    private val peopleApiService: PeopleApiService,
    private val peopleDao: PeopleDao,
    private val popularPeopleEntityMapper: PopularPeopleEntityMapper,
    private val peopleDtoMapper: PeopleDtoMapper,
    private val popularPeopleDtoToEntityMapper: PopularPeopleDtoToEntityMapper,
    private val peopleDetailsDtoMapper: PeopleDetailsDtoMapper,
    private val movieByPeopleDtoMapper: MovieByPeopleDtoMapper,
    private val tvShowCastDtoMapper: TvShowCastDtoMapper,
    private val random: Random,
    private val safeApiCaller: SafeApiCaller,
    private val database: MovieDatabase
) : PeopleRepository {

    override suspend fun getPopularPeopleFromDatabase(): List<People> {
        return popularPeopleEntityMapper.map(peopleDao.getPopularPeople())
    }

    override suspend fun getPopularPeopleFromRemote(): List<People> {
        val page = random.nextInt(20) + 1
        val call =
            safeApiCaller.execute { peopleApiService.getPopularPeople(page = page) }.results?.filterNotNull()
                ?: emptyList()
        return peopleDtoMapper.map(call)
    }

    override suspend fun refreshPopularPeople() {
//        refreshWrapper(
//            { peopleApiService.getPopularPeople(random.nextInt(20) + 1) },
//            popularPeopleDtoToEntityMapper::map,
//            peopleDao::insertPopularPeople,
//            clearOldLocalData = peopleDao::clearAllPopularPeople
//        )
        val response = safeApiCaller.execute { peopleApiService.getPopularPeople(random.nextInt(20) + 1) }
        database.withTransaction {
            peopleDao.clearAllPopularPeople()
            val popularPeople = response.results?.filterNotNull()?.map(popularPeopleDtoToEntityMapper::map).orEmpty()
            peopleDao.insertPopularPeople(popularPeople)
        }
    }

    override suspend fun getPersonDetails(personId: Int): PeopleDetails {
        return peopleDetailsDtoMapper.map(safeApiCaller.execute { peopleApiService.getPerson(personId) })
    }

    override suspend fun getMoviesByPerson(personId: Int): List<Movie> {
        return movieByPeopleDtoMapper.map(safeApiCaller.execute { peopleApiService.getMoviesByPerson(personId) }.cast.orEmpty().filterNotNull())
    }

    override suspend fun getTvShowsByPerson(personId: Int): List<TvShow> {
        return tvShowCastDtoMapper.map(safeApiCaller.execute {
            peopleApiService.getTvShowsByPerson(personId)
        }.cast.orEmpty().filterNotNull())
    }
}
