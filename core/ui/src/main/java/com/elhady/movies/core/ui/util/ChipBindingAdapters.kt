package com.elhady.movies.core.ui.util

import android.view.LayoutInflater
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import com.elhady.movies.core.ui.R
import com.elhady.movies.core.ui.databinding.GenreChipBinding
import com.elhady.movies.core.ui.interaction.ChipListener
import com.elhady.movies.core.ui.state.UserListUiState
import com.google.android.material.chip.ChipGroup

@BindingAdapter(value = ["app:userListChips", "app:chipListener", "app:selectedIds"], requireAll = false)
fun ChipGroup.setGenreChips(
    chips: List<UserListUiState>?,
    chipListener: ChipListener?,
    selectedIds: List<Int>? = emptyList()
) {
    if (chips == null || chipListener == null) return
    
    removeAllViews()
    val inflater = LayoutInflater.from(context)
    for (chipUiState in chips) {
        val binding = DataBindingUtil.inflate<GenreChipBinding>(
            inflater,
            R.layout.genre_chip,
            this,
            false
        )
        binding.item = chipUiState
        binding.listener = chipListener
        binding.root.post {
            (binding.root as? com.google.android.material.chip.Chip)?.isChecked = 
                selectedIds?.contains(chipUiState.id) == true
        }
        addView(binding.root)
    }
}
