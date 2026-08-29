package com.elhady.movies.feature.watchlist.presentation.watchhistory

import com.elhady.movies.core.ui.resource.StringsRes
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class WatchHistoryRecyclerItemsCreator(private val stringsRes: StringsRes) {

    fun createItems(moviesInDataBase: List<MovieUiState>): List<WatchHistoryRecyclerItem> {
        val moviesForRecyclerView = mutableListOf<WatchHistoryRecyclerItem>()
        var latestDateFound: Date? = null

        for (movie in moviesInDataBase.sortedByDescending { it.dateWatched }) {
            if (isNotSameDay(latestDateFound, movie.dateWatched)) {
                moviesForRecyclerView +=
                    WatchHistoryRecyclerItem.Title(composeTitle(movie.dateWatched))
                latestDateFound = movie.dateWatched
            }
            moviesForRecyclerView += WatchHistoryRecyclerItem.MovieCard(movie)
        }

        return moviesForRecyclerView
    }

    private fun isNotSameDay(date1: Date?, date2: Date?): Boolean {
        if (date1 == null || date2 == null) return true
        val cal1 = Calendar.getInstance().apply { time = date1 }
        val cal2 = Calendar.getInstance().apply { time = date2 }
        return cal1.get(Calendar.YEAR) != cal2.get(Calendar.YEAR) ||
                cal1.get(Calendar.DAY_OF_YEAR) != cal2.get(Calendar.DAY_OF_YEAR)
    }

    private fun composeTitle(movieWatchedDate: Date?): String {
        if (movieWatchedDate == null) return ""
        val now = Calendar.getInstance()
        val watched = Calendar.getInstance().apply { time = movieWatchedDate }

        return when {
            isSameDay(now, watched) -> stringsRes.today
            isYesterday(now, watched) -> stringsRes.yesterday
            else -> SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(movieWatchedDate)
        }
    }

    private fun isSameDay(cal1: Calendar, cal2: Calendar): Boolean {
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
    }

    private fun isYesterday(now: Calendar, watched: Calendar): Boolean {
        val yesterday = now.clone() as Calendar
        yesterday.add(Calendar.DAY_OF_YEAR, -1)
        return isSameDay(yesterday, watched)
    }
}
