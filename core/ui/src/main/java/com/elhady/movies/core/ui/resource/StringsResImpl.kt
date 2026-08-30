package com.elhady.movies.core.ui.resource

import android.content.Context
import com.elhady.movies.core.ui.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StringsResImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : StringsRes {
    override val noTrailer: String = context.getString(R.string.no_trailer)
    override val theRequestFailed: String = context.getString(R.string.the_request_failed)
    override val noNetworkConnection: String = context.getString(R.string.no_network_connection)
    override val passwordIsRequired: String = context.getString(R.string.password_is_required)
    override val usernameIsRequired: String = context.getString(R.string.username_is_required)
    override val duplicateEntity: String = context.getString(R.string.duplicate_entry)
    override val someThingError: String = context.getString(R.string.some_thing_error)
    override val addSuccessfully: String = context.getString(R.string.added_successfully)
    override val newListAddSuccessFully: String = context.getString(R.string.new_list_was_added_successfully)
    override val ratingAddSuccessFully: String = context.getString(R.string.rating_was_added_successfully)
    override val notLoggedInToRate: String = context.getString(R.string.you_re_not_logged_in_to_rate)
    override val someThingErrorWhenAddRating: String = context.getString(R.string.something_went_wrong_please_try_again_later)
    override val emptyField: String = context.getString(R.string.empty_field)
    override val watchlist: String = context.getString(R.string.watchlist)
    override val favourite: String = context.getString(R.string.favorite)
    override val popularMovies: String = context.getString(R.string.popular)
    override val upcomingMovies: String = context.getString(R.string.upcoming)
    override val nowPlayingMovies: String = context.getString(R.string.now_playing)
    override val mysteryMovies: String = context.getString(R.string.mystery)
    override val popularActors: String = context.getString(R.string.popular_actors)
    override val adventureMovies: String = context.getString(R.string.adventure)
    override val popularTvShow: String = context.getString(R.string.popular)
    override val topRatedTvShow: String = context.getString(R.string.top_rated)
    override val onTheAirTvShow: String = context.getString(R.string.on_the_air)
    override val airingTodayTvShow: String = context.getString(R.string.airing_today)
    override val topRatedMovies: String = context.getString(R.string.top_rated)
    override val trendingMovies: String = context.getString(R.string.trending)
    override val timeOut: String = context.getString(R.string.time_out)
    override val today: String = context.getString(R.string.today)
    override val yesterday: String = context.getString(R.string.yesterday)
}
