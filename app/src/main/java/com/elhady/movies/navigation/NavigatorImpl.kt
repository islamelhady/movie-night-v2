package com.elhady.movies.navigation

import android.app.Activity
import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.findNavController
import com.elhady.movies.R
import com.elhady.movies.core.common.navigation.Navigator
import com.elhady.movies.core.domain.model.ShowMoreType
import javax.inject.Inject

class NavigatorImpl @Inject constructor(
    private val activity: Activity
) : Navigator {

    private val navController: NavController?
        get() = try {
            activity.findNavController(R.id.nav_host_fragment_activity_main)
        } catch (e: Exception) {
            null
        }

    override fun navigateToMovieDetails(movieId: Int) {
        val request = NavDeepLinkRequest.Builder
            .fromUri(Uri.parse("movie://movie_details/$movieId"))
            .build()
        navController?.navigate(request)
    }

    override fun navigateToTvDetails(tvShowId: Int) {
        val request = NavDeepLinkRequest.Builder
            .fromUri(Uri.parse("movie://tv_details/$tvShowId"))
            .build()
        navController?.navigate(request)
    }

    override fun navigateToSeasonDetails(seriesId: Int, seasonNumber: Int) {
        val request = NavDeepLinkRequest.Builder
            .fromUri(Uri.parse("movie://season_details/$seriesId/$seasonNumber"))
            .build()
        navController?.navigate(request)
    }

    override fun navigateToEpisodeDetails(seriesId: Int, seasonNumber: Int, episodeNumber: Int) {
        val request = NavDeepLinkRequest.Builder
            .fromUri(Uri.parse("movie://episode_details/$seriesId/$seasonNumber/$episodeNumber"))
            .build()
        navController?.navigate(request)
    }

    override fun navigateToPeopleDetails(personId: Int) {
        val request = NavDeepLinkRequest.Builder
            .fromUri(Uri.parse("movie://people_details/$personId"))
            .build()
        navController?.navigate(request)
    }

    override fun navigateToShowMore(showMoreType: ShowMoreType) {
        val request = NavDeepLinkRequest.Builder
            .fromUri(Uri.parse("movie://show_more/$showMoreType"))
            .build()
        navController?.navigate(request)
    }

    override fun navigateToTrailer(videoKey: String) {
        val request = NavDeepLinkRequest.Builder
            .fromUri(Uri.parse("movie://trailer/$videoKey"))
            .build()
        navController?.navigate(request)
    }

    override fun navigateToLogin() {
        val request = NavDeepLinkRequest.Builder
            .fromUri(Uri.parse("movie://login"))
            .build()
        navController?.navigate(request)
    }

    override fun navigateToMyList() {
        val request = NavDeepLinkRequest.Builder
            .fromUri(Uri.parse("movie://my_list"))
            .build()
        navController?.navigate(request)
    }

    override fun navigateToMyListDetails(listId: Int, listType: String, listName: String) {
        val request = NavDeepLinkRequest.Builder
            .fromUri(Uri.parse("movie://my_list_details/$listId/$listType/$listName"))
            .build()
        navController?.navigate(request)
    }

    override fun navigateToWatchHistory() {
        val request = NavDeepLinkRequest.Builder
            .fromUri(Uri.parse("movie://watch_history"))
            .build()
        navController?.navigate(request)
    }

    override fun navigateToMyRated() {
        val request = NavDeepLinkRequest.Builder
            .fromUri(Uri.parse("movie://my_rated"))
            .build()
        navController?.navigate(request)
    }

    override fun navigateToSearch() {
        val request = NavDeepLinkRequest.Builder
            .fromUri(Uri.parse("movie://search"))
            .build()
        navController?.navigate(request)
    }

    override fun navigateToHome() {
        val request = NavDeepLinkRequest.Builder
            .fromUri(Uri.parse("movie://home"))
            .build()
        navController?.navigate(request)
    }

    override fun navigateToSaveToList() {
        val request = NavDeepLinkRequest.Builder
            .fromUri(Uri.parse("movie://save_to_list"))
            .build()
        navController?.navigate(request)
    }

    override fun navigateBack() {
        navController?.popBackStack()
    }
}
