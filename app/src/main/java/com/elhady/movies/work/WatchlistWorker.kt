package com.elhady.movies.work

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.elhady.movies.core.domain.usecase.account.GetMyWatchlistListUseCase
import com.elhady.movies.ui.notification.NotificationHandler
import com.elhady.movies.core.domain.utils.Clock
import com.elhady.movies.core.datastore.local.PreferenceStorage
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@HiltWorker
class WatchlistWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val getMyWatchlistListUseCase: GetMyWatchlistListUseCase,
    private val notificationHandler: NotificationHandler,
    private val clock: Clock,
    private val preferenceStorage: PreferenceStorage
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        Log.d("WatchlistWorker", "doWork: Started")
        return try {
            val todayDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(clock.now()))
            Log.d("WatchlistWorker", "doWork: Today is $todayDate")
            
            // Avoid duplicate runs today
            if (preferenceStorage.lastWatchlistNotificationDate == todayDate) {
                Log.d("WatchlistWorker", "doWork: Already notified today")
                return Result.success()
            }

            val watchlist = getMyWatchlistListUseCase()
            Log.d("WatchlistWorker", "doWork: Watchlist size: ${watchlist.size}")

            watchlist.forEach { item ->
                Log.d("WatchlistWorker", "doWork: Checking ${item.title}, release: ${item.year}")
                if (item.year == todayDate) {
                    Log.d("WatchlistWorker", "doWork: Triggering notification for ${item.title}")
                    notificationHandler.showWatchlistNotification(
                        title = "Watchlist Reminder",
                        message = "Time to watch! ${item.title} is out today.",
                        mediaId = item.id
                    )
                }
            }
            
            preferenceStorage.setLastWatchlistNotificationDate(todayDate)
            Log.d("WatchlistWorker", "doWork: Success")
            Result.success()
        } catch (e: Exception) {
            Log.e("WatchlistWorker", "doWork: Failed", e)
            Result.retry()
        }
    }
}
