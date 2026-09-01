package com.elhady.movies.core.ui.resource

import com.elhady.movies.core.ui.base.UiText
import com.elhady.movies.core.ui.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StringsResImpl @Inject constructor() : StringsRes {
    override val noTrailer: UiText = UiText.Resource(R.string.no_trailer)
    override val theRequestFailed: UiText = UiText.Resource(R.string.the_request_failed)
    override val noNetworkConnection: UiText = UiText.Resource(R.string.no_network_connection)
    override val passwordIsRequired: UiText = UiText.Resource(R.string.password_is_required)
    override val usernameIsRequired: UiText = UiText.Resource(R.string.username_is_required)
    override val duplicateEntity: UiText = UiText.Resource(R.string.duplicate_entry)
    override val someThingError: UiText = UiText.Resource(R.string.some_thing_error)
    override val addSuccessfully: UiText = UiText.Resource(R.string.added_successfully)
    override val newListAddSuccessFully: UiText = UiText.Resource(R.string.new_list_was_added_successfully)
    override val ratingAddSuccessFully: UiText = UiText.Resource(R.string.rating_was_added_successfully)
    override val notLoggedInToRate: UiText = UiText.Resource(R.string.you_re_not_logged_in_to_rate)
    override val someThingErrorWhenAddRating: UiText = UiText.Resource(R.string.something_went_wrong_please_try_again_later)
    override val emptyField: UiText = UiText.Resource(R.string.empty_field)
    override val watchlist: UiText = UiText.Resource(R.string.watchlist)
    override val favourite: UiText = UiText.Resource(R.string.favorite)
    override val popularMovies: UiText = UiText.Resource(R.string.popular)
    override val upcomingMovies: UiText = UiText.Resource(R.string.upcoming)
    override val nowPlayingMovies: UiText = UiText.Resource(R.string.now_playing)
    override val mysteryMovies: UiText = UiText.Resource(R.string.mystery)
    override val popularActors: UiText = UiText.Resource(R.string.popular_actors)
    override val adventureMovies: UiText = UiText.Resource(R.string.adventure)
    override val popularTvShow: UiText = UiText.Resource(R.string.popular)
    override val topRatedTvShow: UiText = UiText.Resource(R.string.top_rated)
    override val onTheAirTvShow: UiText = UiText.Resource(R.string.on_the_air)
    override val airingTodayTvShow: UiText = UiText.Resource(R.string.airing_today)
    override val topRatedMovies: UiText = UiText.Resource(R.string.top_rated)
    override val trendingMovies: UiText = UiText.Resource(R.string.trending)
    override val timeOut: UiText = UiText.Resource(R.string.time_out)
    override val today: UiText = UiText.Resource(R.string.today)
    override val yesterday: UiText = UiText.Resource(R.string.yesterday)
}
