package com.elhady.repository.mediaDataSource.series

import javax.inject.Inject

class SeriesDataSourceContainer @Inject constructor(
    val latestTVDataSource: LatestTVDataSource,
    val onTheAirTVDataSource: OnTheAirTVDataSource,
    val popularTVDataSource: PopularTVDataSource,
    val topRatedTVDataSource: TopRatedTVDataSource,
    val seriesByGenreDataSource: SeriesByGenreDataSource,
    val seriesDataSource: SeriesDataSource
)
