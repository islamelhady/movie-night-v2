package com.elhady.movies.core.data.repository

import com.elhady.movies.core.data.base.BaseRepository
import com.elhady.movies.core.data.mapper.people.LocalPopularPeopleMapper
import com.elhady.movies.core.data.mapper.people.DomainMoviesByPeopleMapper
import com.elhady.movies.core.data.mapper.people.DomainPeopleDetailsMapper
import com.elhady.movies.core.data.mapper.people.DomainPeopleMapper
import com.elhady.movies.core.data.mapper.people.DomainPeopleRemoteMapper
import com.elhady.movies.core.data.mapper.people.DomainTvShowsByPeopleMapper
import com.elhady.movies.core.database.dao.PeopleDao
import com.elhady.movies.core.domain.model.movie.Movie
import com.elhady.movies.core.domain.model.people.PeopleDetails
import com.elhady.movies.core.domain.model.people.People
import com.elhady.movies.core.domain.model.tvshow.TvShow
import com.elhady.movies.core.domain.repository.PeopleRepository
import com.elhady.movies.core.network.api.PeopleApiService
import java.util.Random
import javax.inject.Inject

class PeopleRepositoryImpl @Inject constructor(
    private val peopleApiService: PeopleApiService,
    private val peopleDao: PeopleDao,
    private val domainPeopleMapper: DomainPeopleMapper,
    private val domainPeopleRemoteMapper: DomainPeopleRemoteMapper,
    private val localPopularPeopleMapper: LocalPopularPeopleMapper,
    private val domainPeopleDetailsMapper: DomainPeopleDetailsMapper,
    private val domainMoviesByPeopleMapper: DomainMoviesByPeopleMapper,
    private val tvShowsByPeopleMapper: DomainTvShowsByPeopleMapper,
    private val random: Random
) : BaseRepository(), PeopleRepository {

    override suspend fun getPopularPeopleFromDatabase(): List<People> {
        return domainPeopleMapper.map(peopleDao.getPopularPeople())
    }

    override suspend fun getPopularPeopleFromRemote(): List<People> {
        val page = random.nextInt(20) + 1
        val call =
            wrapApiCall { peopleApiService.getPopularPeople(page = page) }.results?.filterNotNull()
                ?: emptyList()
        return domainPeopleRemoteMapper.map(call)
    }

    override suspend fun refreshPopularPeople() {
        refreshWrapper(
            { peopleApiService.getPopularPeople(random.nextInt(20) + 1) },
            localPopularPeopleMapper::map,
            peopleDao::insertPopularPeople,
            clearOldLocalData = peopleDao::clearAllPopularPeople
        )
    }

    override suspend fun getPersonDetails(personId: Int): PeopleDetails {
        return domainPeopleDetailsMapper.map(wrapApiCall { peopleApiService.getPerson(personId) })
    }

    override suspend fun getMoviesByPerson(personId: Int): List<Movie> {
        return domainMoviesByPeopleMapper.map(wrapApiCall { peopleApiService.getMoviesByPerson(personId) }.cast!!.filterNotNull())
    }

    override suspend fun getTvShowsByPerson(personId: Int): List<TvShow> {
        return tvShowsByPeopleMapper.map(wrapApiCall {
            peopleApiService.getTvShowsByPerson(personId)
        }.cast!!.filterNotNull())
    }
}
