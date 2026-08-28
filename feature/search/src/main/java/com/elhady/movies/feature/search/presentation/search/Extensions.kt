package com.elhady.movies.feature.search.presentation.search

import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import com.elhady.movies.feature.search.R
import com.elhady.movies.feature.search.databinding.SearchChipsFilterItemBinding
import com.google.android.material.chip.ChipGroup

fun <T> ChipGroup.createChip(item: T, listener: SearchAdapterListener): View {
    val binding: SearchChipsFilterItemBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.search_chips_filter_item,
        this,
        false
    )
    binding.item = item as SearchUiState.GenresUiState
    binding.listener = listener
    return binding.root
}
