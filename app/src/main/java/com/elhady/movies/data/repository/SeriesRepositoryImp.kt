package com.elhady.movies.data.repository

import com.elhady.movies.data.local.database.entity.series.OnTheAirSeriesEntity
import com.elhady.movies.data.remote.service.MovieService
import kotlinx.coroutines.flow.Flow

class SeriesRepositoryImp(private val movieService: MovieService) : BaseRepository(), SeriesRepository {
    override suspend fun getOnTheAirSeries(): Flow<List<OnTheAirSeriesEntity>> {
        TODO("Not yet implemented")
    }


}