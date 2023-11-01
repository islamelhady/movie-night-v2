package com.elhady.movies.data.repository.mediaDataSource.series

import javax.inject.Inject

class SeriesDataSourceContainer @Inject constructor(
    val latestTVDataSource: LatestTVDataSource,
    val onTheAirTVDataSource: OnTheAirTVDataSource,
    val popularTVDataSource: PopularTVDataSource,
    val topRatedTVDataSource: TopRatedTVDataSource
)
