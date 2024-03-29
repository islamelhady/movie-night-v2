package com.elhady.movies.data.repository.mediaDataSource.movies

import javax.inject.Inject

class MovieDataSourceContainer @Inject constructor(
    val adventureMovieDataSource: AdventureMovieDataSource,
    val mysteryMovieDataSource: MysteryMovieDataSource,
    val nowPlayingMovieDataSource: NowPlayingMovieDataSource,
    val trendingMovieDataSource: TrendingMovieDataSource,
    val upcomingMovieDataSource: UpcomingMovieDataSource,
    val topRatedMovieDataSource: TopRatedMovieDataSource,
    val moviesByGenreDataSource: MoviesByGenreDataSource,
    val moviesDataSource: MoviesDataSource
)