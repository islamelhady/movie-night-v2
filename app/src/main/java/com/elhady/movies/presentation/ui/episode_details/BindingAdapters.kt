package com.elhady.movies.presentation.ui.episode_details

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@BindingAdapter("app:setVideoId")
fun setVideoId(view: YouTubePlayerView, videoId: String?) {
    view.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
        override fun onReady(youTubePlayer: YouTubePlayer) {
            videoId?.let { youTubePlayer.cueVideo(it, 0f) }
        }
    })
}
@BindingAdapter(value = ["app:hideWhenNoProductionCode"])
fun <T> View.hideWhenNoProductionCode(productionCode:String){
    if (productionCode.isBlank()){
        this.visibility = View.GONE
    }else{
        this.visibility = View.VISIBLE
    }
}

@BindingAdapter(value = ["app:hideWhenNoResultText"])
fun <T> View.hideWhenNoResultText(text:String){
    if (text.isEmpty()){
        this.visibility = View.GONE
    }else{
        this.visibility = View.VISIBLE
    }
}

@BindingAdapter("app:setRefreshing")
fun setRefreshing(view: SwipeRefreshLayout, refreshing: Boolean) {
    view.isRefreshing = refreshing
}
