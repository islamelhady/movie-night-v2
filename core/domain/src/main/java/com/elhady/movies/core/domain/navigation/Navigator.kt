package com.elhady.movies.core.domain.navigation

import com.elhady.movies.core.domain.model.ShowMoreType

interface Navigator {
    fun navigateToMovieDetails(movieId: Int)
    fun navigateToTvDetails(tvShowId: Int)
    fun navigateToSeasonDetails(seriesId: Int, seasonNumber: Int)
    fun navigateToEpisodeDetails(seriesId: Int, seasonNumber: Int, episodeNumber: Int)
    fun navigateToPeopleDetails(personId: Int)
    fun navigateToShowMore(showMoreType: ShowMoreType)
    fun navigateToTrailer(videoKey: String)
    fun navigateToLogin()
    fun navigateToMyList()
    fun navigateToMyListDetails(listId: Int, listType: String, listName: String)
    fun navigateToWatchHistory()
    fun navigateToMyRated()
    fun navigateToSearch()
    fun navigateToHome()
    fun navigateToSaveToList()
    fun navigateBack()
}
