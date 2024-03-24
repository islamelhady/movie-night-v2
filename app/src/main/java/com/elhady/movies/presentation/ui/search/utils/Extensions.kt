package com.elhady.movies.presentation.ui.search.utils

import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import com.elhady.movies.R
import com.elhady.movies.databinding.SearchChipsFilterItemBinding
import com.elhady.movies.presentation.viewmodel.search.SearchListener
import com.elhady.movies.presentation.viewmodel.search.SearchUiState
import com.google.android.material.chip.ChipGroup

fun <T> ChipGroup.createChip(item: T, listener: SearchListener): View {
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