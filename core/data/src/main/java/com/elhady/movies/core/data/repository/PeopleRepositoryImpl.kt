package com.elhady.movies.core.data.repository

import com.elhady.movies.core.data.base.BaseRepository
import com.elhady.movies.core.data.mapper.people.PopularPeopleDtoToEntityMapper
import com.elhady.movies.core.data.mapper.people.MoviesByPeopleDtoMapper
import com.elhady.movies.core.data.mapper.people.PeopleDetailsDtoMapper
import com.elhady.movies.core.data.mapper.people.PopularPeopleEntityMapper
import com.elhady.movies.core.data.mapper.people.PeopleDtoMapper
import com.elhady.movies.core.data.mapper.people.TvShowsCastDtoMapper
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
    private val popularPeopleEntityMapper: PopularPeopleEntityMapper,
    private val peopleDtoMapper: PeopleDtoMapper,
    private val popularPeopleDtoToEntityMapper: PopularPeopleDtoToEntityMapper,
    private val peopleDetailsDtoMapper: PeopleDetailsDtoMapper,
    private val moviesByPeopleDtoMapper: MoviesByPeopleDtoMapper,
    private val tvShowsByPeopleMapper: TvShowsCastDtoMapper,
    private val random: Random
) : BaseRepository(), PeopleRepository {

    override suspend fun getPopularPeopleFromDatabase(): List<People> {
        return popularPeopleEntityMapper.map(peopleDao.getPopularPeople())
    }

    override suspend fun getPopularPeopleFromRemote(): List<People> {
        val page = random.nextInt(20) + 1
        val call =
            wrapApiCall { peopleApiService.getPopularPeople(page = page) }.results?.filterNotNull()
                ?: emptyList()
        return peopleDtoMapper.map(call)
    }

    override suspend fun refreshPopularPeople() {
        refreshWrapper(
            { peopleApiService.getPopularPeople(random.nextInt(20) + 1) },
            popularPeopleDtoToEntityMapper::map,
            peopleDao::insertPopularPeople,
            clearOldLocalData = peopleDao::clearAllPopularPeople
        )
    }

    override suspend fun getPersonDetails(personId: Int): PeopleDetails {
        return peopleDetailsDtoMapper.map(wrapApiCall { peopleApiService.getPerson(personId) })
    }

    override suspend fun getMoviesByPerson(personId: Int): List<Movie> {
        return moviesByPeopleDtoMapper.map(wrapApiCall { peopleApiService.getMoviesByPerson(personId) }.cast!!.filterNotNull())
    }

    override suspend fun getTvShowsByPerson(personId: Int): List<TvShow> {
        return tvShowsByPeopleMapper.map(wrapApiCall {
            peopleApiService.getTvShowsByPerson(personId)
        }.cast!!.filterNotNull())
    }
}
