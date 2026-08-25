package com.elhady.movies.feature.details.presentation.tvdetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elhady.movies.core.ui.base.BaseFragment
import com.elhady.movies.core.ui.navigation.Navigator
import com.elhady.movies.core.ui.state.UserListUiState
import com.elhady.movies.feature.details.R
import com.elhady.movies.feature.details.databinding.FragmentTvDetailsBinding
import com.elhady.movies.feature.details.presentation.tvdetails.adapter.TvDetailsAdapter
import com.elhady.movies.feature.details.presentation.tvdetails.listener.BottomSheetDismissListener
import com.elhady.movies.feature.details.presentation.tvdetails.listener.TvDetailsListeners
import com.elhady.movies.feature.details.presentation.tvdetails.listener.WatchlistFavouriteListener
import com.elhady.movies.feature.details.presentation.tvdetails.state.TvDetailsUIState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.math.abs
import com.elhady.movies.core.ui.R as CoreUiR

@AndroidEntryPoint
class TvDetailsFragment :
    BaseFragment<FragmentTvDetailsBinding, TvDetailsUIState, TvDetailsUiEffect>(),
    BottomSheetDismissListener, WatchlistFavouriteListener, TvDetailsListeners {

    @Inject
    lateinit var navigator: Navigator

    private lateinit var rateBottomSheet: RateTvDetailsBottomSheet
    private lateinit var saveTvShowToListBottomSheet: SaveTvShowToListBottomSheet
    private lateinit var tvDetailsAdapter: TvDetailsAdapter

    override val layoutIdFragment: Int = R.layout.fragment_tv_details
    override val viewModel: TvDetailsViewModel by viewModels()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(
            view,
            savedInstanceState
        )
        binding.listener = this
        setupAdapter()
        setupCollapseBehavior()
    }


    private fun setupAdapter() {

        tvDetailsAdapter = TvDetailsAdapter(
            mutableListOf(),
            this
        )

        binding.nestedRecycler.adapter =
            tvDetailsAdapter
    }

    override fun onRateButtonClick() {
        viewModel.onEvent(TvDetailsUiEvent.RateClicked)
    }

    override fun onClickPeople(id: Int) {
        viewModel.onEvent(TvDetailsUiEvent.PersonClicked(id))
    }

    override fun onClickMedia(id: Int) {
        viewModel.onEvent(TvDetailsUiEvent.RecommendationClicked(id))
    }

    override fun onClickSeason(seasonNumber: Int) {
        viewModel.onEvent(TvDetailsUiEvent.SeasonClicked(seasonNumber))
    }

    override fun onShowMoreCast() {
        viewModel.onEvent(TvDetailsUiEvent.ShowMoreCastClicked)
    }

    override fun onShowMoreRecommended() {
        viewModel.onEvent(TvDetailsUiEvent.ShowMoreRecommendedClicked)
    }

    override fun onClickPlayButton() {
        viewModel.onEvent(TvDetailsUiEvent.PlayClicked)
    }

    override fun onChipClick(id: Int) {
        viewModel.onEvent(TvDetailsUiEvent.ListSelected(id))
    }

    override fun onClickBack() {
        viewModel.onEvent(TvDetailsUiEvent.BackClicked)
    }

    override fun onSaveClicked() {
        viewModel.onEvent(TvDetailsUiEvent.SaveClicked)
    }

    override fun onClickTryAgain() {
        viewModel.onEvent(TvDetailsUiEvent.Retry)
    }

    override fun render(state: TvDetailsUIState) {
        tvDetailsAdapter.setItems(state.tvDetailsItems)
        binding.state = state
    }

    override fun onEffect(effect: TvDetailsUiEffect) {
        when (effect) {

            TvDetailsUiEffect.NavigateBack -> {
                navigator.navigateBack()
            }

            is TvDetailsUiEffect.NavigateToPersonDetails -> {
                navigator.navigateToPeopleDetails(
                    effect.personId
                )
            }

            is TvDetailsUiEffect.NavigateToSeasonDetails -> {
                navigator.navigateToSeasonDetails(
                    effect.tvShowId,
                    effect.seasonNumber
                )
            }

            is TvDetailsUiEffect.NavigateToTvDetails -> {
                navigator.navigateToTvDetails(
                    effect.tvShowId
                )
            }

            is TvDetailsUiEffect.NavigateToTrailer -> {
                navigator.navigateToTrailer(
                    effect.youtubeKey
                )
            }

            TvDetailsUiEffect.NavigateToShowMoreCast -> {
                // Navigate when destination exists.
            }

            TvDetailsUiEffect.NavigateToShowMoreRecommendation -> {
                // Navigate when destination exists.
            }

            is TvDetailsUiEffect.ShowRatingBottomSheet -> {
                showRateBottomSheet()
            }

            is TvDetailsUiEffect.ShowSaveToListBottomSheet -> {
                showSaveToListBottomSheet(effect.lists)
            }

            is TvDetailsUiEffect.ShowSnackBar -> {
                showSnackBar(effect.message)
            }
        }
    }


    private fun showRateBottomSheet() {

        rateBottomSheet =
            RateTvDetailsBottomSheet()

        rateBottomSheet.setListener(this)

        rateBottomSheet.show(
            childFragmentManager,
            RATE_BOTTOM_SHEET_TAG
        )
    }

    override fun onApplyRateBottomSheet() {

        viewModel.onEvent(
            TvDetailsUiEvent.RatingSubmitted
        )
    }

    override fun updateRatingValue(
        rate: Float
    ) {

        viewModel.onEvent(
            TvDetailsUiEvent.RatingChanged(rate)
        )
    }

    override fun getUserRating(): Float {

        return viewModel.state.value.ratingUIState.rating.div(2)
    }

    private fun showSaveToListBottomSheet(lists: List<UserListUiState>) {

        binding.saveButton.setBackgroundResource(
            CoreUiR.drawable.ic_save_pressed
        )

        saveTvShowToListBottomSheet =
            SaveTvShowToListBottomSheet(object : WatchlistFavouriteListener {
                override fun onFavourite() {
                    viewModel.onEvent(TvDetailsUiEvent.FavouriteClicked)
                }

                override fun onWatchlist() {
                    viewModel.onEvent(TvDetailsUiEvent.WatchlistClicked)
                }

                override fun onDone() {
                    viewModel.onEvent(TvDetailsUiEvent.DoneAddingLists)
                    saveTvShowToListBottomSheet.dismiss()
                }

                override fun onChipClick(id: Int) {
                    viewModel.onEvent(TvDetailsUiEvent.ListSelected(id))
                }

                override fun onCreateList(name: String) {
                    viewModel.onEvent(TvDetailsUiEvent.CreateNewListClicked(name))
                }

                override fun onDismiss() {
                    saveTvShowToListBottomSheet.dismiss()
                }
            })

        saveTvShowToListBottomSheet.setItems(lists)

        saveTvShowToListBottomSheet.show(
            childFragmentManager,
            SAVE_TO_LIST_BOTTOM_SHEET_TAG
        )
    }

    override fun onFavourite() {

        viewModel.onEvent(
            TvDetailsUiEvent.FavouriteClicked
        )
    }

    override fun onWatchlist() {

        viewModel.onEvent(
            TvDetailsUiEvent.WatchlistClicked
        )
    }

    override fun onDone() {
        viewModel.onEvent(
            TvDetailsUiEvent.DoneAddingLists
        )
    }

    override fun onDismiss() {
        binding.saveButton.setBackgroundResource(
            CoreUiR.drawable.ic_save_unpressed
        )
    }

    override fun onCreateList(name: String) {
        viewModel.onEvent(
            TvDetailsUiEvent.CreateNewListClicked(name)
        )
    }


    private fun setupCollapseBehavior() {

        var firstVisiblePosition = 0

        binding.nestedRecycler.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {

                override fun onScrolled(
                    recyclerView: RecyclerView,
                    dx: Int,
                    dy: Int
                ) {
                    val layoutManager =
                        recyclerView.layoutManager
                                as? LinearLayoutManager
                            ?: return

                    firstVisiblePosition =
                        layoutManager.findFirstVisibleItemPosition()
                }
            }
        )

        binding.appBarLayout.addOnOffsetChangedListener { appBarLayout,
                                                          verticalOffset ->

            when {

                verticalOffset == 0 -> {

                    binding.textViewToolBarName.visibility =
                        View.INVISIBLE

                    if (firstVisiblePosition != 0) {
                        appBarLayout.setExpanded(
                            false,
                            false
                        )
                    }
                }

                abs(verticalOffset) >=
                        appBarLayout.totalScrollRange -> {

                    binding.textViewToolBarName.visibility =
                        View.VISIBLE
                }

                else -> {

                    binding.textViewToolBarName.visibility =
                        View.INVISIBLE
                }
            }
        }
    }

    private companion object {

        const val RATE_BOTTOM_SHEET_TAG =
            "RATE_TV_DETAILS"

        const val SAVE_TO_LIST_BOTTOM_SHEET_TAG =
            "SAVE_TV_SHOW_TO_LIST"
    }

}
