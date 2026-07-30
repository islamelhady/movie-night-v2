package com.elhady.movies.core.ui.resource

import android.content.Context
import androidx.annotation.StringRes
import com.elhady.movies.core.ui.R
import com.elhady.movies.core.ui.base.StringsRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StringsResImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : StringsRes {
    override val theRequestFailed: String = getString(R.string.the_request_failed)
    override val noNetworkConnection: String = getString(R.string.no_network_connection)
    override val passwordIsRequired: String = getString(R.string.password_is_required)
    override val usernameIsRequired: String = getString(R.string.username_is_required)
    override val duplicateEntity: String = getString(R.string.duplicate_entry)
    override val someThingError: String = getString(R.string.some_thing_error)
    override val addSuccessfully: String = getString(R.string.added_successfully)
    override val newListAddSuccessFully: String = getString(R.string.new_list_was_added_successfully)
    override val ratingAddSuccessFully: String = getString(R.string.rating_was_added_successfully)
    override val notLoggedInToRate: String = getString(R.string.your_not_loged_in_to_rate)
    override val someThingErrorWhenAddRating: String = getString(R.string.something_went_wrong_please_try_again_later)
    override val watchlist: String = getString(R.string.watchlist)
    override val favourite: String = getString(R.string.favorite)
    override val popularMovies: String = getString(R.string.popular)
    override val upcomingMovies: String = getString(R.string.upcoming)
    override val nowPlayingMovies: String = getString(R.string.now_playing)
    override val mysteryMovies: String = getString(R.string.mystery)
    override val popularActors: String = getString(R.string.popular_actors)
    override val adventureMovies: String = getString(R.string.adventure)
    override val popularTvShow: String = getString(R.string.popular)
    override val topRatedTvShow: String = getString(R.string.top_rated)
    override val onTheAirTvShow: String = getString(R.string.on_the_air)
    override val airingTodayTvShow: String = getString(R.string.airing_today)
    override val topRatedMovies: String = getString(R.string.top_rated)
    override val trendingMovies: String = getString(R.string.trending)
    override val timeOut: String = getString(R.string.time_out)
    override val today: String = getString(R.string.today)
    override val yesterday: String = getString(R.string.yesterday)

    private fun getString(@StringRes stringsRes: Int): String {
        return context.getString(stringsRes)
    }

    private fun getString(@StringRes stringsRes: Int, vararg args: Any): String {
        return context.getString(stringsRes, args)
    }
}
