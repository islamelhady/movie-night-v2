package com.elhady.movies.ui.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.elhady.movies.MovieApp
import com.elhady.movies.ui.MainActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NotificationHandler @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun showWatchlistNotification(title: String, message: String, mediaId: Int) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("mediaId", mediaId)
        }
        
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            context, mediaId, intent, PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(context, MovieApp.WATCHLIST_CHANNEL_ID)
            .setSmallIcon(com.elhady.movies.core.ui.R.drawable.ic_movie)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(mediaId, builder.build())
    }
}
