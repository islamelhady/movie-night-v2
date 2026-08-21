package com.elhady.movies.feature.details.presentation.tvdetails

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import com.elhady.movies.core.ui.base.BaseBottomSheet
import com.elhady.movies.core.ui.interaction.ChipListener
import com.elhady.movies.core.ui.state.UserListUiState
import com.elhady.movies.feature.details.R
import com.elhady.movies.feature.details.databinding.SaveTvShowToListBottomSheetTvCreateListBinding
import com.elhady.movies.feature.details.presentation.tvdetails.listener.WatchlistFavouriteListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SaveTvShowToListBottomSheet(private val watchlistFavouriteBottomSheet: WatchlistFavouriteListener) :
    BaseBottomSheet<SaveTvShowToListBottomSheetTvCreateListBinding>(), ChipListener {
    override val layoutIdFragment: Int = R.layout.save_tv_show_to_list_bottom_sheet_tv_create_list
    override val viewModel = GenericViewModel()
    override val viewModelVariableId: Int = 0

    private var userLists: List<UserListUiState> = emptyList()

    fun setItems(lists: List<UserListUiState>) {
        this.userLists = lists
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.items = userLists
        binding.chipListener = this

        binding.chipAddNewList.visibility = View.GONE
        binding.apply {
            chipAddNewList.setOnClickListener {
                groupCreateList.visibility =
                    if (chipAddNewList.isChecked) View.VISIBLE else View.GONE
            }
            textViewClose.setOnClickListener {
                dismiss()
            }
            materialButtonCreate.setOnClickListener {
                val listName = textInputEditTextListName.text.toString()
                if (listName.isNotEmpty()) {
                    watchlistFavouriteBottomSheet.onCreateList(listName)
                    textInputEditTextListName.text?.clear()
                }
            }
        }

        binding.textViewDone.setOnClickListener {
            if (binding.chipFavourite.isChecked) watchlistFavouriteBottomSheet.onFavourite()
            if (binding.chipWatchlist.isChecked) watchlistFavouriteBottomSheet.onWatchlist()
            watchlistFavouriteBottomSheet.onDone()
            dismiss()
        }
    }

    override fun onChipClick(id: Int) {
        watchlistFavouriteBottomSheet.onChipClick(id)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        watchlistFavouriteBottomSheet.onDismiss()
    }
}
