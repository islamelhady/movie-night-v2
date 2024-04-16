package com.elhady.movies.presentation.ui.watchhistory

import com.elhady.movies.core.bases.StringsRes
import com.elhady.movies.presentation.viewmodel.watchhistory.MovieUiState
import java.util.Date
import kotlin.math.abs

class WatchHistoryRecyclerItemsCreator(private val stringsRes: StringsRes) {
    private val currentDate = Date()

    fun createItems(moviesInDataBase: List<MovieUiState>): List<WatchHistoryRecyclerItem> {
        val moviesForRecyclerView = mutableListOf<WatchHistoryRecyclerItem>()
        var latestDateFound: Date? = null

        for (movie in moviesInDataBase.sortedByDescending { it.dateWatched }) {
            if (isNotSameTitle(latestDateFound, movie)) {
                moviesForRecyclerView +=
                    WatchHistoryRecyclerItem.Title(composeTitle(movie.dateWatched))
                latestDateFound = movie.dateWatched
            }
            moviesForRecyclerView += WatchHistoryRecyclerItem.MovieCard(movie)
        }

        return moviesForRecyclerView
    }

    private fun isNotSameTitle(
        latestDateFound: Date?,
        movie: MovieUiState
    ): Boolean {
        return latestDateFound == null ||
                movie.dateWatched?.getDaysDifferenceCount(latestDateFound) != THE_SAME_DAY
    }

    private fun composeTitle(movieWatchedDate: Date?): String {
        if (movieWatchedDate == null) return ""
        return when (currentDate.getDaysDifferenceCount(movieWatchedDate)) {
            THE_SAME_DAY -> stringsRes.today
            YESTERDAY -> stringsRes.yesterday
            else -> movieWatchedDate.toString().substring(0..9)
        }
    }

    private fun Date.getDaysDifferenceCount(otherDate: Date): Int {
        val firstDateText = this.toString().substring(4..9)
        val otherDateText = otherDate.toString().substring(4..9)
        return when {
            firstDateText == otherDateText -> THE_SAME_DAY
            isOneDayDifference(firstDateText, otherDateText) -> YESTERDAY
            else -> OTHER_DAYS
        }
    }

    private fun isOneDayDifference(firstDateText: String, otherDateText: String): Boolean {
        val month1 = firstDateText.substring(0..2)
        val month2 = otherDateText.substring(0..2)
        val day1 = firstDateText.substring(4..5).toInt()
        val day2 = otherDateText.substring(4..5).toInt()

        return month1 == month2 && abs(day1 - day2) == 1
    }

    private companion object {
        const val THE_SAME_DAY = 0
        const val YESTERDAY = 1
        const val OTHER_DAYS = 1124
    }
}