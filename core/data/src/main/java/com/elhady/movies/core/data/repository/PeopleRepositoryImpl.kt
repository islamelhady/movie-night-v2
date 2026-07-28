package com.elhady.movies.core.data.repository

import com.elhady.movies.core.data.bases.BaseRepository
import com.elhady.movies.core.data.mapper.cache.LocalPopularPeopleMapper
import com.elhady.movies.core.data.mapper.domain.DomainMoviesByPeopleMapper
import com.elhady.movies.core.data.mapper.domain.DomainPeopleDetailsMapper
import com.elhady.movies.core.data.mapper.domain.DomainPeopleMapper
import com.elhady.movies.core.data.mapper.domain.DomainPeopleRemoteMapper
import com.elhady.movies.core.data.mapper.domain.DomainTvShowsByPeopleMapper
import com.elhady.movies.core.database.MovieDao
import com.elhady.movies.core.domain.model.MovieEntity
import com.elhady.movies.core.domain.model.PeopleDetailsEntity
import com.elhady.movies.core.domain.model.PeopleEntity
import com.elhady.movies.core.domain.model.TvShowEntity
import com.elhady.movies.core.domain.repository.PeopleRepository
import com.elhady.movies.core.network.service.MovieService
import java.util.Random
import javax.inject.Inject

class PeopleRepositoryImpl @Inject constructor(
    private val movieService: MovieService,
    private val movieDao: MovieDao,
    private val domainPeopleMapper: DomainPeopleMapper,
    private val domainPeopleRemoteMapper: DomainPeopleRemoteMapper,
    private val localPopularPeopleMapper: LocalPopularPeopleMapper,
    private val domainPeopleDetailsMapper: DomainPeopleDetailsMapper,
    private val domainMoviesByPeopleMapper: DomainMoviesByPeopleMapper,
    private val tvShowsByPeopleMapper: DomainTvShowsByPeopleMapper,
    private val random: Random
) : BaseRepository(), PeopleRepository {

    override suspend fun getPopularPeopleFromDatabase(): List<PeopleEntity> {
        return domainPeopleMapper.map(movieDao.getPopularPeople())
    }

    override suspend fun getPopularPeopleFromRemote(): List<PeopleEntity> {
        val page = random.nextInt(20) + 1
        val call =
            wrapApiCall { movieService.getPopularPeople(page = page) }.results?.filterNotNull()
                ?: emptyList()
        return domainPeopleRemoteMapper.map(call)
    }

    override suspend fun refreshPopularPeople() {
        refreshWrapper(
            { movieService.getPopularPeople(random.nextInt(20) + 1) },
            localPopularPeopleMapper::map,
            movieDao::insertPopularPeople,
            clearOldLocalData = movieDao::clearAllPopularPeople
        )
    }

    override suspend fun getPersonDetails(personId: Int): PeopleDetailsEntity {
        return domainPeopleDetailsMapper.map(wrapApiCall { movieService.getPerson(personId) })
    }

    override suspend fun getMoviesByPerson(personId: Int): List<MovieEntity> {
        return domainMoviesByPeopleMapper.map(wrapApiCall { movieService.getMoviesByPerson(personId) }.cast!!)
    }

    override suspend fun getTvShowsByPerson(personId: Int): List<TvShowEntity> {
        return tvShowsByPeopleMapper.map(wrapApiCall {
            movieService.getTvShowsByPerson(personId)
        }.cast!!)
    }
}
