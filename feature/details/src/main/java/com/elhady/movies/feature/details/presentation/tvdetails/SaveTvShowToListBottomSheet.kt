package com.elhady.movies.feature.details.presentation.tvdetails

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.elhady.movies.feature.details.BR
import com.elhady.movies.core.ui.base.BaseBottomSheet
import com.elhady.movies.core.ui.interaction.ChipListener
import com.elhady.movies.core.ui.state.UserListUiState
import com.elhady.movies.feature.details.R
import com.elhady.movies.feature.details.databinding.SaveTvShowToListBottomSheetTvCreateListBinding
import com.elhady.movies.feature.details.presentation.tvdetails.listener.WatchlistFavouriteListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SaveTvShowToListBottomSheet :
    BaseBottomSheet<SaveTvShowToListBottomSheetTvCreateListBinding>(), ChipListener {
    override val layoutIdFragment: Int = R.layout.save_tv_show_to_list_bottom_sheet_tv_create_list
    override val viewModel by viewModels<TvDetailsViewModel>({ requireParentFragment() })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.listener = this

        collectFlow(viewModel.effect) { onEffect(it) }

        collectFlow(viewModel.state) {
            binding.state = it
        }
    }

    private fun onEffect(effect: TvDetailsUiEffect) {
        when (effect) {
            TvDetailsUiEffect.AddListToBottomSheet -> {
                binding.groupCreateList.visibility =
                    if (binding.chipAddNewList.isChecked) View.VISIBLE else View.GONE
            }

            TvDetailsUiEffect.CloseBottomSheet -> dismiss()
            else -> {}
        }
    }

    override fun onChipClick(id: Int) {
        viewModel.onEvent(TvDetailsUiEvent.ListSelected(id))
    }

    fun onDismiss() = dismiss()
    fun onDone() = viewModel.onEvent(TvDetailsUiEvent.DoneAddingLists)
    fun onCreateList(name: String) = viewModel.onEvent(TvDetailsUiEvent.CreateNewListClicked(name))
    fun onFavourite() = viewModel.onEvent(TvDetailsUiEvent.FavouriteClicked)
    fun onWatchlist() = viewModel.onEvent(TvDetailsUiEvent.WatchlistClicked)
    fun onAddList() = viewModel.onEvent(TvDetailsUiEvent.AddNewListClicked)
}
