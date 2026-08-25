package com.elhady.movies.feature.watchlist.presentation.mylistdetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.elhady.movies.core.ui.base.BaseFragment
import com.elhady.movies.core.ui.navigation.Navigator
import com.elhady.movies.feature.watchlist.R
import com.elhady.movies.feature.watchlist.databinding.FragmentMyListDetailsBinding
import com.elhady.movies.feature.watchlist.presentation.mylistdetails.adapter.MyListDetailsAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyListDetailsFragment :
    BaseFragment<FragmentMyListDetailsBinding, MyListDetailsUiState, MyListDetailsUiEffect>(),
    MyListDetailsAdapterListener, MyListDetailsListener {

    @Inject
    lateinit var navigator: Navigator

    override val layoutIdFragment: Int =
        R.layout.fragment_my_list_details

    override val viewModel: MyListDetailsViewModel by viewModels()

    private val adapter: MyListDetailsAdapter by lazy {
        MyListDetailsAdapter(
            items = emptyList(),
            listener = this
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        collectState()
        binding.listener = this
    }

    private fun setupRecyclerView() {
        binding.recyclerViewMyListDetails.adapter = adapter
    }


    private fun collectState() {
        collectFlow(viewModel.state) { state ->
            render(state)
        }
    }

    private fun render(state: MyListDetailsUiState) {
        binding.state = state
        adapter.setItems(state.movies)
    }

    override fun onEffect(effect: MyListDetailsUiEffect) {
        when (effect) {

            is MyListDetailsUiEffect.NavigateToMovieDetails -> {
                navigator.navigateToMovieDetails(
                    effect.movieId
                )
            }

            is MyListDetailsUiEffect.NavigateToTvShowDetails -> {
                navigator.navigateToTvDetails(
                    effect.tvShowId
                )
            }

            MyListDetailsUiEffect.NavigateBack -> {
                navigator.navigateBack()
            }

            is MyListDetailsUiEffect.ShowSnackBar -> {
                showSnackBar(effect.message)
            }
        }
    }

    override fun onClickItem(itemId: Int, mediaType: String) {
        viewModel.onEvent(
            MyListDetailsUiEvent.MovieClicked(itemId)
        )
    }

    override fun onClickBackButton() {
        viewModel.onEvent(
            MyListDetailsUiEvent.BackClicked
        )
    }

    override fun onClickRetry() {
        viewModel.onEvent(
            MyListDetailsUiEvent.RetryClicked
        )
    }
}
