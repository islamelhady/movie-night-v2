package com.elhady.movies.presentation.ui.search.utils

import android.content.res.Configuration
import android.view.View
import android.widget.ImageButton
import android.widget.ToggleButton
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elhady.movies.R
import com.elhady.movies.presentation.viewmodel.search.SearchListener
import com.elhady.movies.presentation.viewmodel.search.SearchUiState
import com.google.android.material.chip.ChipGroup

@BindingAdapter(value = ["app:setGenres", "app:listener", "app:chipSelected"])
fun ChipGroup.setGenres(
    items: List<SearchUiState.GenresUiState>?,
    listener: SearchListener,
    chipSelected: Int?
) {
    this.removeAllViews()
    items?.let {
        it.forEach { genre -> this.addView(this.createChip(genre, listener)) }
    }

    val chipIndex = items?.indexOf(items.find { it.genreId == chipSelected }) ?: 0
    this.getChildAt(chipIndex)?.id?.let { this.check(it) }
}

@BindingAdapter(value = ["app:selectedMedia"])
fun ChipGroup.setSelectedMedia(media: SearchUiState.SearchMedia) {
    when (media) {
        SearchUiState.SearchMedia.MOVIE -> check(R.id.chipMovie)
        SearchUiState.SearchMedia.TV -> check(R.id.chipTV)
        SearchUiState.SearchMedia.PEOPLE -> check(R.id.chipPerson)
    }
}

@BindingAdapter(value = ["app:hideImageButton","app:query"])
fun ImageButton.setHideImageButton(hide: Boolean?, query: String?) {
    this.visibility = if (hide == true || query.isNullOrEmpty()) View.GONE else View.VISIBLE
}

@BindingAdapter(value = ["app:searchLayoutManager"])
fun RecyclerView.setSearchLayoutManager(searchUiState: SearchUiState?) {
    val layoutManager = when (searchUiState?.mediaType) {
        SearchUiState.SearchMedia.MOVIE, SearchUiState.SearchMedia.TV -> {
            LinearLayoutManager(context)
        }
        SearchUiState.SearchMedia.PEOPLE -> {
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                GridLayoutManager(context, 8)
            } else {
                GridLayoutManager(context, 5)
            }
        }
        else -> {
            LinearLayoutManager(context)
        }
    }
    this.layoutManager = layoutManager
}

@BindingAdapter("app:exploreLayoutManagerToggle")
fun RecyclerView.setLayoutManagerToggle(useGrid: Boolean) {
    val layoutManager = if (useGrid) {
        GridLayoutManager(context, 2)
    } else {
        LinearLayoutManager(context)
    }
    this.layoutManager = layoutManager
}