package com.elhady.movies.presentation.ui.tvdetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elhady.movies.R
import com.elhady.movies.core.bases.BaseFragment
import com.elhady.movies.databinding.FragmentTvDetailsBinding
import com.elhady.movies.presentation.ui.tvdetails.adapter.TvDetailsAdapter
import com.elhady.movies.presentation.viewmodel.tvdetails.TvDetailsUiEvent
import com.elhady.movies.presentation.viewmodel.tvdetails.TvDetailsUiState
import com.elhady.movies.presentation.viewmodel.tvdetails.TvDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

@AndroidEntryPoint
class TvDetailsFragment :
    BaseFragment<FragmentTvDetailsBinding, TvDetailsUiState, TvDetailsUiEvent>(),
    BottomSheetDismissListener, WatchlistFavouriteListener {

    private lateinit var rateBottomSheet: RateTvDetailsBottomSheet
    private lateinit var addToWatchlistFavouriteBottomSheet: SaveTvShowToListBottomSheet
    private lateinit var tvDetailsAdapter: TvDetailsAdapter
    private val args: TvDetailsFragmentArgs by navArgs()
    override val layoutIdFragment: Int = R.layout.fragment_tv_details
    override val viewModel: TvDetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        collectChange()
        collapseState()
    }

    override fun onEvent(event: TvDetailsUiEvent) {
        when (event) {
            is TvDetailsUiEvent.RateTvEvent -> {
                showRateBottomSheet()
            }

            is TvDetailsUiEvent.OnPersonClick -> {
                findNavController()
                    .navigate(
                        TvDetailsFragmentDirections.actionTvDetailsFragmentToPeopleDetailsFragment(
                            event.id
                        )
                    )
            }
            is TvDetailsUiEvent.OnSeasonClick -> {
                findNavController()
                    .navigate(
                        TvDetailsFragmentDirections.actionTvDetailsFragmentToSeasonDetailsFragment(
                            args.tvShowId,
                            event.seasonNumber
                        )
                    )
            }
            is TvDetailsUiEvent.OnRecommended -> navigateToTvDetails(event.id)
            is TvDetailsUiEvent.Back -> navigateBack()
            is TvDetailsUiEvent.ApplyRating -> showSnackBar(event.message)
            is TvDetailsUiEvent.OnShowMoreCast -> showSnackBar("Show More Cast")
            is TvDetailsUiEvent.OnShowMoreRecommended -> showSnackBar("Show More Recommended")
            is TvDetailsUiEvent.PlayButton -> navigateToTrailerFragment(event.youtubeKey)
            is TvDetailsUiEvent.OnSaveButtonClick -> showAddToWatchlistFavouriteBottomSheet()
            is TvDetailsUiEvent.OnDoneAdding -> showSnackBar(event.message)
            is TvDetailsUiEvent.OnCreateNewList -> showSnackBar(event.message)
            is TvDetailsUiEvent.OnFavourite -> showSnackBar(event.message)
            is TvDetailsUiEvent.OnWatchList -> showSnackBar(event.message)
            is TvDetailsUiEvent.ShowSnackBar -> showSnackBar(event.message)
            else -> {}
        }
    }


    private fun navigateToTrailerFragment(videoKey: String) {
        findNavController().navigate(
            TvDetailsFragmentDirections
                .actionTvDetailsFragmentToTrailerFragment3(videoKey)
        )
        showSnackBar(videoKey)
    }

    private fun setAdapter() {
        tvDetailsAdapter = TvDetailsAdapter(mutableListOf(), viewModel)
        binding.nestedRecycler.adapter = tvDetailsAdapter
    }
    //region navigation
    private fun navigateToTvDetails(tvId: Int) {
        findNavController().navigate(TvDetailsFragmentDirections.actionTvDetailsFragmentSelf(tvId))
    }

    private fun navigateBack() {
        findNavController().popBackStack()
    }
    //endregion

    private fun collectChange() {
        collectLatest {
            viewModel.state.collect { state ->
                val tvDetailsItems = mutableListOf(
                    TvDetailsItem.Upper(state.info),
                    TvDetailsItem.People(state.cast, state.seasons.isNotEmpty()),
                )
                tvDetailsItems.addAll(
                    state.seasons.map { TvDetailsItem.Season(it) }
                        + TvDetailsItem.Recommended(state.recommended, state.reviews.isNotEmpty())
                        + state.reviews.map {
                        TvDetailsItem.Review(it)
                })
                tvDetailsAdapter.setItems(tvDetailsItems)
                binding.nestedRecycler.smoothScrollToPosition(0)
                binding.appBarLayout.setExpanded(true,true)
            }
        }
    }
    //region rating bottom sheet
    private fun showRateBottomSheet() {
        rateBottomSheet = RateTvDetailsBottomSheet()
        rateBottomSheet.setListener(this)
        rateBottomSheet.show(childFragmentManager, "BOTTOM")
    }


    override fun onApplyRateBottomSheet() {
        viewModel.onRatingSubmit()
    }

    override fun updateRatingValue(rate: Float) {
        viewModel.updateRatingUiState(rate)
    }

    override fun getUserRating(): Float {
        return viewModel.state.value.userRating.div(2)
    }
    //endregion

    //region collapse toolbar
    private fun collapseState() {
        var pos = 0
        findNavController().addOnDestinationChangedListener { _, _, _ ->
            binding.nestedRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val firstVisibleItemPosition = recyclerView.layoutManager as LinearLayoutManager
                    pos = firstVisibleItemPosition.findFirstVisibleItemPosition()
                }
            })
            binding.appBarLayout.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
                when {
                    // Fully expanded state
                    verticalOffset == 0 -> {
                        binding.textViewToolBarName.visibility = View.INVISIBLE
                        if (pos != 0) appBarLayout.setExpanded(false, false)
                    }
                    // Fully collapsed state
                    abs(verticalOffset) >= appBarLayout.totalScrollRange -> {
                        binding.textViewToolBarName.visibility = View.VISIBLE
                    }
                    // In between expanded and collapsed states
                    else -> {
                        binding.textViewToolBarName.visibility = View.INVISIBLE
                    }
                }
            }
        }
    }
    //endregion

    //region save to bottom sheet
    private fun showAddToWatchlistFavouriteBottomSheet() {
        binding.saveButton.setBackgroundResource(R.drawable.ic_save_pressed)
        addToWatchlistFavouriteBottomSheet = SaveTvShowToListBottomSheet(this)
        addToWatchlistFavouriteBottomSheet.show(childFragmentManager, "BOTTOM")
    }


    override fun onFavourite() {
        viewModel.addToFavourite()
    }

    override fun onWatchlist() {
        viewModel.addToWatchlist()
    }

    override fun onDismiss() {
        binding.saveButton.setBackgroundResource(R.drawable.ic_save_unpressed)
    }
    //endregion
}