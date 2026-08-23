package com.elhady.movies.feature.details.presentation.moviedetails

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elhady.movies.feature.details.R
import com.elhady.movies.core.ui.R as CoreUiR
import com.elhady.movies.core.ui.base.BaseFragment
import com.elhady.movies.core.ui.interaction.ChipListener
import com.elhady.movies.core.ui.interaction.MediaListener
import com.elhady.movies.core.ui.interaction.PeopleAdapterListener
import com.elhady.movies.feature.details.databinding.FragmentMovieDetailsBinding
import com.elhady.movies.feature.details.presentation.moviedetails.adapter.MovieDetailsAdapter
import com.elhady.movies.feature.details.presentation.moviedetails.adapter.MovieDetailsItem
import com.elhady.movies.feature.details.presentation.tvdetails.listener.BottomSheetDismissListener
import com.elhady.movies.core.ui.navigation.Navigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.math.abs

@AndroidEntryPoint
class MovieDetailsFragment :
    BaseFragment<FragmentMovieDetailsBinding, MovieDetailsUiState, MovieDetailsUiEffect>(),
    BottomSheetDismissListener, MovieDetailsListener, MediaListener, PeopleAdapterListener, ChipListener {

    @Inject
    lateinit var navigator: Navigator

    override val layoutIdFragment: Int = R.layout.fragment_movie_details
    override val viewModel: MovieDetailsViewModel by viewModels()

    private lateinit var movieDetailsAdapter: MovieDetailsAdapter
    private lateinit var rateBottomSheet: RatingMovieBottomSheet
    private lateinit var addToListBottomSheet: SaveMovieToListBottomSheet

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.title = ""
        binding.listener = this
        (activity as AppCompatActivity?)!!.setSupportActionBar(binding.toolbar)
        setAdapter()
        collectChange()
        collapseState()
    }

    private fun setAdapter() {
        // Passing this as all listeners
        movieDetailsAdapter = MovieDetailsAdapter(mutableListOf(), this, this, this)
        binding.nestedRecycler.adapter = movieDetailsAdapter
    }

    private fun collectChange() {
        collectFlow(flow = viewModel.state) { state ->
            binding.state = state
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
        }
    }

    override fun onEffect(effect: MovieDetailsUiEffect) {
        when (effect) {
            MovieDetailsUiEffect.NavigateBack -> navigator.navigateBack()
            is MovieDetailsUiEffect.NavigateToPeopleDetails -> navigator.navigateToPeopleDetails(effect.personId)
            is MovieDetailsUiEffect.NavigateToMovieDetails -> navigator.navigateToMovieDetails(effect.movieId)
            is MovieDetailsUiEffect.ShowSnackBar -> showSnackBar(effect.message)
            MovieDetailsUiEffect.ShowRateBottomSheet -> showRateBottomSheet()
            is MovieDetailsUiEffect.ShowSaveToListBottomSheet -> showSaveToListBottomSheet()
            is MovieDetailsUiEffect.NavigateToShowMore -> {
                // TODO: Implement show more
            }
            is MovieDetailsUiEffect.PlayVideoTrailer -> navigator.navigateToTrailer(effect.videoKey)
            else -> {}
        }
    }

    // Interaction Listeners mapping to events
    override fun onClickPlayTrailer() = viewModel.onEvent(MovieDetailsUiEvent.PlayClicked)
    override fun onClickRateMovie() = viewModel.onEvent(MovieDetailsUiEvent.RateClicked)
    override fun onClickBackButton() = viewModel.onEvent(MovieDetailsUiEvent.BackClicked)
    override fun onClickShowMore(movieId: Int) = viewModel.onEvent(MovieDetailsUiEvent.ShowMoreClicked(movieId))
    override fun onClickSaveButton() = viewModel.onEvent(MovieDetailsUiEvent.SaveClicked)
    override fun tryAgain(movieId: Int) = viewModel.onEvent(MovieDetailsUiEvent.RetryClicked(movieId))
    override fun onClickMedia(id: Int) = viewModel.onEvent(MovieDetailsUiEvent.MovieClicked(id))
    override fun onPeopleClick(id: Int) = viewModel.onEvent(MovieDetailsUiEvent.PersonClicked(id))
    override fun onChipClick(id: Int) = viewModel.onEvent(MovieDetailsUiEvent.ChipClicked(id))

    // Fragment UI Logic
    private fun showRateBottomSheet() {
        rateBottomSheet = RatingMovieBottomSheet()
        rateBottomSheet.setListener(this)
        rateBottomSheet.show(childFragmentManager, "BOTTOM")
    }

    private fun showSaveToListBottomSheet() {
        binding.saveButton.setBackgroundResource(CoreUiR.drawable.ic_save_pressed)
        addToListBottomSheet = SaveMovieToListBottomSheet()
        addToListBottomSheet.show(childFragmentManager, "SAVE_LIST")
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
                    verticalOffset == 0 -> {
                        binding.textViewToolBarName.visibility = View.INVISIBLE
                        if (pos != 0) appBarLayout.setExpanded(false, false)
                    }
                    abs(verticalOffset) >= appBarLayout.totalScrollRange -> {
                        binding.textViewToolBarName.visibility = View.VISIBLE
                        binding.nestedRecycler.isNestedScrollingEnabled = true
                    }
                    else -> {
                        binding.textViewToolBarName.visibility = View.INVISIBLE
                    }
                }
            }
        }
    }

    // BottomSheetDismissListener
    override fun onApplyRateBottomSheet() = viewModel.onEvent(MovieDetailsUiEvent.RatingSubmitted)
    override fun updateRatingValue(rate: Float) = viewModel.onEvent(MovieDetailsUiEvent.RatingChanged(rate))
    override fun getUserRating(): Float = viewModel.state.value.userRating / 2
}
