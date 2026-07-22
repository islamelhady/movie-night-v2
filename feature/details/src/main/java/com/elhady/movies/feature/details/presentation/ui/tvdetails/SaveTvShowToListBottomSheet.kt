package com.elhady.movies.feature.details.presentation.ui.tvdetails

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.elhady.movies.feature.details.BR
import com.elhady.movies.feature.details.R
import com.elhady.movies.core.common.bases.BaseBottomSheet
import com.elhady.movies.feature.details.databinding.SaveTvShowToListBottomSheetTvCreateListBinding
import com.elhady.movies.feature.details.presentation.tvdetails.TvDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SaveTvShowToListBottomSheet(private val watchlistFavouriteBottomSheet: WatchlistFavouriteListener,) :
    BaseBottomSheet<SaveTvShowToListBottomSheetTvCreateListBinding>() {
    override val layoutIdFragment: Int= R.layout.save_tv_show_to_list_bottom_sheet_tv_create_list
    override val viewModel by activityViewModels<TvDetailsViewModel>()
    override val viewModelVariableId: Int = BR.viewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        collectLatest {
            viewModel.event.collectLatest {
//                onEvent(it)
            }
        }

        binding.chipAddNewList.visibility = View.GONE
        binding.apply {
//            viewModel = viewModel
//            lifecycleOwner = viewLifecycleOwner
            chipAddNewList.setOnClickListener {
                groupCreateList.visibility =
                    if (chipAddNewList.isChecked) View.VISIBLE else View.GONE
            }
            textViewClose.setOnClickListener {
                dismiss()
            }
        }
        viewModel.getUserLists()
        binding.textViewDone.setOnClickListener {
            if (binding.chipFavourite.isChecked) watchlistFavouriteBottomSheet.onFavourite()
            if (binding.chipWatchlist.isChecked) watchlistFavouriteBottomSheet.onWatchlist()
            dismiss()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        watchlistFavouriteBottomSheet.onDismiss()
    }
}

interface WatchlistFavouriteListener {
    fun onFavourite()
    fun onWatchlist()
    fun onDismiss()
}
