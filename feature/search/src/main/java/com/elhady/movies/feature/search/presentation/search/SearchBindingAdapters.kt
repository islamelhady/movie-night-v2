package com.elhady.movies.feature.search.presentation.search

import android.content.res.Configuration
import android.view.View
import android.widget.ImageButton
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elhady.movies.core.ui.base.ErrorUiState
import com.elhady.movies.feature.search.R
import com.google.android.material.chip.ChipGroup

@BindingAdapter(value = ["app:hideResult", "app:query"], requireAll = false)
fun View.hideResult(error: ErrorUiState?, query: String?) {
    this.visibility = if (error != null) View.GONE else View.VISIBLE
}

@BindingAdapter(value = ["app:showWhenQueryEmpty"])
fun View.showWhenEmptyData(query: String?) {
    if (query?.isEmpty() == true) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}

@BindingAdapter(value = ["app:showWhenQueryEmpty", "app:showWhenFailure"])
fun View.showBasedOnState(
    query: String?,
    error: ErrorUiState?
) {
    visibility = when {
        query.isNullOrEmpty() && error == null -> View.VISIBLE
        else -> View.GONE
    }
}

@BindingAdapter(value = ["app:showWhenFailure"])
fun View.showBasedOnState(
    error: ErrorUiState?
) {
    visibility = when {
        error != null -> View.VISIBLE
        else -> View.GONE
    }
}

@BindingAdapter(value = ["app:setGenres", "app:listener", "app:chipSelected"])
fun ChipGroup.setGenres(
    items: List<SearchUiState.GenresUiState>?,
    listener: SearchFilterListener,
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
fun ChipGroup.setSelectedMedia(media: SearchUiState.SearchMedia?) {
    when (media) {
        SearchUiState.SearchMedia.MOVIE -> check(R.id.chipMovie)
        SearchUiState.SearchMedia.TV -> check(R.id.chipTV)
        SearchUiState.SearchMedia.PEOPLE -> check(R.id.chipPerson)
        else -> {}
    }
}

@BindingAdapter(value = ["app:hideImageButton","app:query"])
fun ImageButton.setHideImageButton(hide: Boolean?, query: String?) {
    this.visibility = if (hide == true || query.isNullOrEmpty()) View.GONE else View.VISIBLE
}

@BindingAdapter(value = ["app:searchLayoutManager"])
fun RecyclerView.setSearchLayoutManager(searchUiState: SearchUiState?) {
    val currentLayoutManager = this.layoutManager
    val requiredType = when (searchUiState?.mediaType) {
        SearchUiState.SearchMedia.MOVIE, SearchUiState.SearchMedia.TV -> {
            LinearLayoutManager::class
        }

        SearchUiState.SearchMedia.PEOPLE -> {
            GridLayoutManager::class
        }

        else -> LinearLayoutManager::class
    }

    if (currentLayoutManager == null || !requiredType.isInstance(currentLayoutManager)) {
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
}
