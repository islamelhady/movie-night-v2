package com.elhady.movies.feature.details.presentation.moviedetails

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elhady.movies.feature.details.BR
import com.elhady.movies.feature.details.R
import com.elhady.movies.core.ui.R as CoreUiR
import com.elhady.movies.core.ui.base.BaseFragment
import com.elhady.movies.feature.details.databinding.FragmentMovieDetailsBinding
import com.elhady.movies.feature.details.presentation.moviedetails.adapter.MovieDetailsAdapter
import com.elhady.movies.feature.details.presentation.moviedetails.adapter.MovieDetailsItem
import com.elhady.movies.feature.details.presentation.tvdetails.BottomSheetDismissListener
import com.elhady.movies.core.ui.navigation.Navigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.math.abs


@AndroidEntryPoint
class MovieDetailsFragment :
    BaseFragment<FragmentMovieDetailsBinding, MovieDetailsUiState, MovieDetailsUiEvent>(), BottomSheetDismissListener{

    @Inject
    lateinit var navigator: Navigator

    override val layoutIdFragment: Int = R.layout.fragment_movie_details
    override val viewModel: MovieDetailsViewModel by viewModels()
    override val viewModelVariableId: Int = BR.viewModel

    private lateinit var movieDetailsAdapter: MovieDetailsAdapter
    private lateinit var rateBottomSheet: RatingMovieBottomSheet
    private lateinit var addToListBottomSheet: SaveMovieToListBottomSheet
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.title = ""
        (activity as AppCompatActivity?)!!.setSupportActionBar(binding.toolbar)
        setAdapter()
        collectChange()
        collapseState()
    }

    private fun setAdapter() {
        movieDetailsAdapter = MovieDetailsAdapter(mutableListOf(), viewModel, viewModel, viewModel)
        binding.nestedRecycler.adapter = movieDetailsAdapter
    }

    private fun collectChange() {
        collectFlow(flow = viewModel.state) { state ->
                movieDetailsAdapter.setItems(
                    mutableListOf(
                        MovieDetailsItem.Upper(state.movieUiState),
                        MovieDetailsItem.People(state.castUiState),
                        MovieDetailsItem.Recommended(
                            state.recommendedUiState,
                            state.reviewUiState.isEmpty(),
                            state.id,
                            state.reviewsDetails.totalReviews,
                            state.reviewsDetails.totalPages > 1
                        ),

                        ) + state.reviewUiState.map { MovieDetailsItem.Reviews(it) }
                )
                binding.nestedRecycler.smoothScrollToPosition(0)
                binding.appBarLayout.setExpanded(true, true)
            }
        }

    override fun onEffect(effect: MovieDetailsUiEvent) {
        when (effect) {
            MovieDetailsUiEvent.OnClickBackEvent -> {
                navigator.navigateBack()
            }

            is MovieDetailsUiEvent.NavigateToPeopleDetailsEvent -> {
                navigator.navigateToPeopleDetails(effect.itemId)
            }

            is MovieDetailsUiEvent.ApplyRatingEvent -> {
                showSnackBar(effect.message)
            }

            is MovieDetailsUiEvent.PlayVideoTrailerEvent -> {
                navigator.navigateToTrailer(effect.videoKey)
            }

            is MovieDetailsUiEvent.RateMovieEvent -> {
                showRateBottomSheet()
            }

            is MovieDetailsUiEvent.NavigateToMovieDetailsEvent -> {
                navigator.navigateToMovieDetails(effect.movieId)
            }

            is MovieDetailsUiEvent.SaveToListEvent -> {
                binding.saveButton.setBackgroundResource(CoreUiR.drawable.ic_save_pressed)
                navigator.navigateToSaveToList()
            }


            is MovieDetailsUiEvent.NavigateToShowMoreEvent -> {
                // TODO: Implement show more
            }

            is MovieDetailsUiEvent.ShowSnackBarMessageEvent -> showSnackBar(effect.message)
            else -> {}
        }
    }

    private fun showRateBottomSheet() {
        rateBottomSheet = RatingMovieBottomSheet()
        rateBottomSheet.setListener(this)
        rateBottomSheet.show(childFragmentManager, "BOTTOM")
    }


    private fun collapseState() {
        collectFlow(flow = viewModel.state) { state ->
                binding.nestedRecycler.isNestedScrollingEnabled =
                    !(state.reviewUiState.isEmpty() && state.recommendedUiState.isEmpty())
        }

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
                        binding.nestedRecycler.isNestedScrollingEnabled = true
                    }
                    // In between expanded and collapsed states
                    else -> {
                        binding.textViewToolBarName.visibility = View.INVISIBLE
                    }
                }
            }
        }
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

}
