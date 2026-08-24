package com.elhady.movies.feature.watchlist.presentation.watchhistory

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.elhady.movies.feature.watchlist.BR
import com.elhady.movies.feature.watchlist.R
import com.elhady.movies.core.ui.R as CoreUiR
import com.elhady.movies.core.ui.base.BaseFragment
import com.elhady.movies.core.ui.interaction.MediaListener
import com.elhady.movies.feature.watchlist.databinding.FragmentWatchHistoryBinding
import com.elhady.movies.core.ui.util.SwipeToDeleteItem
import com.elhady.movies.feature.watchlist.presentation.watchhistory.adapter.WatchHistoryAdapter
import com.elhady.movies.core.ui.navigation.Navigator
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WatchHistoryFragment : BaseFragment<FragmentWatchHistoryBinding, WatchHistoryUiState, WatchHistoryUiEffect>(),
    MediaListener {
    @Inject
    lateinit var navigator: Navigator

    override val layoutIdFragment: Int =
        R.layout.fragment_watch_history

    override val viewModel: WatchHistoryViewModel by viewModels()

    override val viewModelVariableId: Int =
        BR.viewModel

    private lateinit var adapter: WatchHistoryAdapter
    private val deletionIndicatorSnackBar by lazy {
        setupSnackBar()
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSwipeToDelete()
        collectState()
    }

    private fun setupRecyclerView() {
        adapter = WatchHistoryAdapter(
            items = mutableListOf(),
            listener = this
        )

        binding.watchHistoryRecyclerView.adapter = adapter
    }

    private fun collectState() {
        collectFlow(viewModel.state) { state ->
            binding.state = state
            adapter.setItems(state.movies)
        }
    }

    private fun setupSwipeToDelete() {
        val swipeGesture = object : SwipeToDeleteItem() {

            override fun onSwiped(
                viewHolder: RecyclerView.ViewHolder,
                direction: Int
            ) {
                if (direction == ItemTouchHelper.LEFT) {
                    viewModel.onEvent(
                        WatchHistoryUiEvent.MovieSwiped(
                            position = viewHolder.absoluteAdapterPosition
                        )
                    )
                }
            }
        }

        ItemTouchHelper(swipeGesture)
            .attachToRecyclerView(binding.watchHistoryRecyclerView)
    }

    override fun onEffect(effect: WatchHistoryUiEffect) {
        when (effect) {

            is WatchHistoryUiEffect.NavigateToMovieDetails -> {
                navigator.navigateToMovieDetails(effect.movieId)
            }

            WatchHistoryUiEffect.ShowDeleteSnackBar -> {
                deletionIndicatorSnackBar.show()
            }

            is WatchHistoryUiEffect.ShowErrorSnackBar -> {
                showSnackBar(
                    getString(CoreUiR.string.cannot_fetch_movies)
                )
            }

            WatchHistoryUiEffect.NavigateBack -> {
                navigator.navigateBack()
            }
        }
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
                    CoreUiR.color.orange_red
                )
            )
            .setAction(
                getString(CoreUiR.string.undo)
            ) {
                viewModel.onEvent(
                    WatchHistoryUiEvent.UndoDeleteClicked
                )
            }
            .addCallback(
                object :
                    BaseTransientBottomBar.BaseCallback<Snackbar>() {

                    override fun onDismissed(
                        transientBottomBar: Snackbar?,
                        event: Int
                    ) {
                        if (
                            event != Snackbar.Callback.DISMISS_EVENT_ACTION
                        ) {
                            viewModel.onEvent(
                                WatchHistoryUiEvent
                                    .DeleteSnackBarDismissed
                            )
                        }

                        super.onDismissed(
                            transientBottomBar,
                            event
                        )
                    }
                }
            )
    }

    override fun onStop() {
        super.onStop()

        viewModel.onEvent(
            WatchHistoryUiEvent.DeleteSnackBarDismissed
        )
    }

    override fun onClickMedia(id: Int) {
        viewModel.onEvent(WatchHistoryUiEvent.MovieClicked(id))
    }
}
