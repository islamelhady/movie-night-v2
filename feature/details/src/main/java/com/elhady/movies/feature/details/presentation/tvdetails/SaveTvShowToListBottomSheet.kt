package com.elhady.movies.feature.details.presentation.tvdetails

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.elhady.movies.feature.details.BR
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
    override val viewModel: TvDetailsViewModel by activityViewModels()
    override val viewModelVariableId: Int = BR.viewModel

    private var userLists: List<UserListUiState> = emptyList()

    fun setItems(lists: List<UserListUiState>) {
        this.userLists = lists
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.items = userLists
        binding.listener = watchlistFavouriteBottomSheet

        binding.chipAddNewList.visibility = View.GONE
        binding.apply {
            chipAddNewList.setOnClickListener {
                groupCreateList.visibility =
                    if (chipAddNewList.isChecked) View.VISIBLE else View.GONE
            }
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
