package com.elhady.movies.feature.details.presentation.tvdetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elhady.movies.core.ui.base.BaseFragment
import com.elhady.movies.core.ui.navigation.Navigator
import com.elhady.movies.feature.details.BR
import com.elhady.movies.feature.details.R
import com.elhady.movies.feature.details.databinding.FragmentTvDetailsBinding
import com.elhady.movies.feature.details.presentation.tvdetails.adapter.TvDetailsAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.math.abs
import com.elhady.movies.core.ui.R as CoreUiR

@AndroidEntryPoint
class TvDetailsFragment :
    BaseFragment<FragmentTvDetailsBinding, TvDetailsUiState, TvDetailsUiEvent>(),
    BottomSheetDismissListener, WatchlistFavouriteListener {

    @Inject
    lateinit var navigator: Navigator

    private lateinit var rateBottomSheet: RateTvDetailsBottomSheet
    private lateinit var addToWatchlistFavouriteBottomSheet: SaveTvShowToListBottomSheet
    private lateinit var tvDetailsAdapter: TvDetailsAdapter
    
    override val layoutIdFragment: Int = R.layout.fragment_tv_details
    override val viewModel: TvDetailsViewModel by viewModels()
    override val viewModelVariableId: Int = BR.viewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        collectChange()
        collapseState()
    }

    override fun onEffect(effect: TvDetailsUiEvent) {
        when (effect) {
            is TvDetailsUiEvent.RateTvEvent -> {
                showRateBottomSheet()
            }

            is TvDetailsUiEvent.OnPersonClick -> {
                navigator.navigateToPeopleDetails(effect.id)
            }
            is TvDetailsUiEvent.OnSeasonClick -> {
                navigator.navigateToSeasonDetails(viewModel.state.value.id, effect.seasonNumber)
            }
            is TvDetailsUiEvent.OnRecommended -> navigator.navigateToTvDetails(effect.id)
            is TvDetailsUiEvent.Back -> navigator.navigateBack()
            is TvDetailsUiEvent.ApplyRating -> showSnackBar(effect.message)
            is TvDetailsUiEvent.OnShowMoreCast -> showSnackBar("Show More Cast")
            is TvDetailsUiEvent.OnShowMoreRecommended -> showSnackBar("Show More Recommended")
            is TvDetailsUiEvent.PlayButton -> {
                navigator.navigateToTrailer(effect.youtubeKey)
                showSnackBar(effect.youtubeKey)
            }
            is TvDetailsUiEvent.OnSaveButtonClick -> showAddToWatchlistFavouriteBottomSheet()
            is TvDetailsUiEvent.OnDoneAdding -> showSnackBar(effect.message)
            is TvDetailsUiEvent.OnCreateNewList -> showSnackBar(effect.message)
            is TvDetailsUiEvent.OnFavourite -> showSnackBar(effect.message)
            is TvDetailsUiEvent.OnWatchList -> showSnackBar(effect.message)
            is TvDetailsUiEvent.ShowSnackBar -> showSnackBar(effect.message)
            else -> {}
        }
    }


    private fun navigateToTrailerFragment(videoKey: String) {
        navigator.navigateToTrailer(videoKey)
        showSnackBar(videoKey)
    }

    private fun setAdapter() {
        tvDetailsAdapter = TvDetailsAdapter(mutableListOf(), viewModel)
        binding.nestedRecycler.adapter = tvDetailsAdapter
    }
    
    private fun navigateToTvDetails(tvId: Int) {
        navigator.navigateToTvDetails(tvId)
    }

    private fun navigateBack() {
        navigator.navigateBack()
    }

    private fun collectChange() {
        collectFlow(flow = viewModel.state) { state ->
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
                    verticalOffset == 0 -> {
                        binding.textViewToolBarName.visibility = View.INVISIBLE
                        if (pos != 0) appBarLayout.setExpanded(false, false)
                    }
                    abs(verticalOffset) >= appBarLayout.totalScrollRange -> {
                        binding.textViewToolBarName.visibility = View.VISIBLE
                    }
                    else -> {
                        binding.textViewToolBarName.visibility = View.INVISIBLE
                    }
                }
            }
        }
    }

    private fun showAddToWatchlistFavouriteBottomSheet() {
        binding.saveButton.setBackgroundResource(CoreUiR.drawable.ic_save_pressed)
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
        binding.saveButton.setBackgroundResource(CoreUiR.drawable.ic_save_unpressed)
    }
}
