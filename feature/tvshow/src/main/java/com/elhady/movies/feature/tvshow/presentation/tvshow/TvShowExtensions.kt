package com.elhady.movies.feature.tvshow.presentation.tvshow

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.elhady.movies.core.ui.adapter.BaseFooterAdapter
import com.elhady.movies.feature.tvshow.presentation.tvshow.adapter.TvShowAdapter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun GridLayoutManager.setSpanSize(
    footerAdapter: BaseFooterAdapter,
    adapter: TvShowAdapter,
    spanCount: Int
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

fun <T> LifecycleOwner.collectLast(flow: Flow<T>, action: suspend (T) -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collectLatest(action)
        }
    }
}
