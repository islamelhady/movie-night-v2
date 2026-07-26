package com.elhady.movies.feature.watchlist.presentation.ui.watchhistory

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.elhady.movies.feature.watchlist.BR
import com.elhady.movies.feature.watchlist.R
import com.elhady.movies.core.ui.R as CoreUiR
import com.elhady.movies.core.common.bases.BaseFragment
import com.elhady.movies.feature.watchlist.databinding.FragmentWatchHistoryBinding
import com.elhady.movies.core.ui.bases.SwipeToDeleteItem
import com.elhady.movies.feature.watchlist.presentation.watchhistory.WatchHistoryViewModel
import com.elhady.movies.feature.watchlist.presentation.watchhistory.WatchHistoryUiEvent
import com.elhady.movies.feature.watchlist.presentation.watchhistory.WatchHistoryUiState
import com.elhady.movies.core.domain.navigation.Navigator
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WatchHistoryFragment
    : BaseFragment<FragmentWatchHistoryBinding, WatchHistoryUiState, WatchHistoryUiEvent>() {

    @Inject
    lateinit var navigator: Navigator

    override val layoutIdFragment = R.layout.fragment_watch_history
    override val viewModel by viewModels<WatchHistoryViewModel>()
    override val viewModelVariableId: Int = BR.viewModel
    private lateinit var adapter: WatchHistoryAdapter
    private val deletionIndicatorSnackBar by lazy {
        setupSnackBar()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerAdapter()
        swipeToDeleteItemSetup(binding.watchHistoryRecyclerView)
    }

    private fun setupRecyclerAdapter() {
        adapter = WatchHistoryAdapter(mutableListOf(), viewModel)
        binding.watchHistoryRecyclerView.adapter = adapter
    }


    override fun onEvent(event: WatchHistoryUiEvent) {
        when (event) {
            is WatchHistoryUiEvent.NavigateToMovieDetails -> navigator.navigateToMovieDetails(event.movieId)
            is WatchHistoryUiEvent.ShowDeleteSnackBar -> deletionIndicatorSnackBar.show()
            is WatchHistoryUiEvent.Error -> showSnackBar(getString(CoreUiR.string.cannot_fetch_movies))
            is WatchHistoryUiEvent.OnClickBack -> onBackButtonPressed()
        }
    }

    private fun swipeToDeleteItemSetup(itemRv: RecyclerView) {
        val swipeGesture = swipeGestureAnonymousObject()
        val touchHelper = ItemTouchHelper(swipeGesture)
        touchHelper.attachToRecyclerView(itemRv)
    }

    private fun swipeGestureAnonymousObject() = object : SwipeToDeleteItem() {
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            try {
                finishNotCompletedDeletion()
                handleSwipes(direction, viewHolder.absoluteAdapterPosition)
            } catch (e: Exception) { }
        }
    }

    private fun finishNotCompletedDeletion() {
        viewModel.deleteItemFromDataBase()
        viewModel.initTheDeletionStates()
    }

    private fun handleSwipes(direction: Int, position: Int) {
        when (direction) {
            ItemTouchHelper.LEFT -> {
                onSwipeLeftActions(position)
            }
        }
    }

    private fun onSwipeLeftActions(position: Int) {
        viewModel.setPosition(position)
        viewModel.deleteItemFromUi()
    }

    private fun setupSnackBar(): Snackbar {
        return Snackbar.make(
            binding.root,
            getString(CoreUiR.string.item_deleted),
            Snackbar.LENGTH_LONG
        )
            .setAnimationMode(Snackbar.ANIMATION_MODE_FADE)
            .setActionTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    CoreUiR.color.orangeRed
                )
            )
            .addCallback(setupSnackBarCallback())
            .setAction(getString(CoreUiR.string.undo)) {
                viewModel.addItemToUi()
            }
    }

    private fun setupSnackBarCallback() = object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
        override fun onDismissed(
            transientBottomBar: Snackbar?,
            event: Int
        ) {
            viewModel.deleteItemFromDataBase()
            super.onDismissed(transientBottomBar, event)
        }

        override fun onShown(snackBar: Snackbar?) {
            viewModel.onSnackBarShown()
            super.onShown(snackBar)
        }
    }

    private fun createToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }


    private fun onBackButtonPressed() {
        navigator.navigateBack()
    }

    override fun onStop() {
        super.onStop()
        viewModel.deleteItemFromDataBase()
    }
}
