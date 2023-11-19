package com.elhady.movies.utilities

import android.content.res.Resources
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.elhady.movies.R
import com.elhady.movies.data.remote.response.FavListDto
import com.elhady.movies.data.remote.response.video.ResultDto
import com.elhady.movies.databinding.ItemChipCategoryBinding
import com.elhady.movies.ui.adapter.LoadAdapter
import com.elhady.movies.ui.base.BasePagingAdapter
import com.elhady.movies.ui.category.CategoryGenreUiState
import com.elhady.movies.ui.category.CategoryInteractionListener
import com.google.android.material.chip.ChipGroup
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

fun <T> LifecycleOwner.collectLast(flow: Flow<T>, action: suspend (T) -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collectLatest(action)
        }
    }
}

fun <T> LifecycleOwner.collect(flow: Flow<T>, action: suspend (T) -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect(action)
        }
    }
}

fun <T : Any> GridLayoutManager.setSpanSize(
    footerAdapter: LoadAdapter, adapter: BasePagingAdapter<T>, spanCount: Int
) {
    this.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
            return if ((position == adapter.itemCount) && footerAdapter.itemCount > 0) {
                spanCount
            } else {
                1
            }
        }
    }
}

fun Date.convertToDayMonthYearFormat(): String {
    val formatter = SimpleDateFormat("dd/mm/yyyy", Locale.getDefault())
    return formatter.format(this)
}

@BindingAdapter(value = ["app:movieHours", "app:movieMinutes"])
fun setDuration(view: TextView, hours: Int?, minutes: Int?) {
    if (hours == 0) {
        view.text = String.format(view.context.getString(R.string.minutes_pattern), minutes)
    } else if (minutes == 0) {
        view.text = String.format(view.context.getString(R.string.hours_pattern), hours)
    } else {
        view.text =
            String.format(view.context.getString(R.string.hours_minutes_pattern), hours, minutes)
    }
}

fun <T> ChipGroup.createChip(item: CategoryGenreUiState, listener: T): View {
    val chipBinding: ItemChipCategoryBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context), R.layout.item_chip_category, this, false
    )
    chipBinding.item = item
    chipBinding.listener = listener as CategoryInteractionListener
    return chipBinding.root
}

fun List<ResultDto?>.getKey(): String? = this.map {
    if (it?.type == "Trailer") return it.key
}.toString()

@BindingAdapter("app:setVideoId")
fun setVideoId(view: YouTubePlayerView, videoId: String?) {
    view.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
        override fun onReady(youTubePlayer: YouTubePlayer) {
            videoId?.let { youTubePlayer.cueVideo(it, 0f) }
        }
    })
}

fun DialogFragment.setWidthPercent(percentage: Int) {
    val percent = percentage.toFloat() / 100
    val dm = Resources.getSystem().displayMetrics
    val rect = dm.run { Rect(0, 0, widthPixels, heightPixels) }
    val percentWidth = rect.width() * percent
    dialog?.window?.setLayout(percentWidth.toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
    dialog?.setCanceledOnTouchOutside(false)
}

fun <T> List<T>.margeTwoList(secondList: List<T>): List<T>{
    return this.plus(secondList)
}

fun FavListDto.checkIfExist(movieId: Int): Boolean {
    this.items?.map {
        if (it?.id == movieId) {
            return true
        }
    }
    return false
}

@BindingAdapter(value = ["app:noError", "app:doneLoad", "app:emptyData"])
fun <T, M> showWhenNoData(view: View, error: List<T>?, loading: Boolean, data: List<M>?) {
    view.isVisible = error.isNullOrEmpty() && !loading && data.isNullOrEmpty()
}