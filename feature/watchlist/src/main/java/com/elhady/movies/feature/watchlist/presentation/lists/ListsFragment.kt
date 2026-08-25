package com.elhady.movies.feature.watchlist.presentation.lists

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.elhady.movies.core.ui.base.BaseFragment
import com.elhady.movies.core.ui.navigation.Navigator
import com.elhady.movies.feature.watchlist.R
import com.elhady.movies.feature.watchlist.databinding.FragmentListsBinding
import com.elhady.movies.feature.watchlist.presentation.lists.adapter.ListsAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.elhady.movies.core.ui.R as CoreUiR

@AndroidEntryPoint
class ListsFragment :
    BaseFragment<FragmentListsBinding, ListsUiState, ListsUiEffect>(),
    ListsListener, ListsAdapterListener, CreateListener {

    @Inject
    lateinit var navigator: Navigator

    override val layoutIdFragment: Int = R.layout.fragment_lists
    override val viewModel: ListsViewModel by viewModels()

    private val listsAdapter: ListsAdapter by lazy {
        ListsAdapter(
            items = mutableListOf(),
            listener = this
        )
    }

    private lateinit var createListBottomSheet: CreateListBottomSheetFragment

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupListeners()
        collectState()
    }

    private fun setupRecyclerView() {
        binding.recyclerViewMyList.adapter = listsAdapter
    }

    private fun setupListeners() {
        binding.toolbar.setNavigationOnClickListener {
            viewModel.onEvent(ListsUiEvent.BackClicked)
        }
        binding.listener = this
    }

    private fun collectState() {
        collectFlow(viewModel.state) { state ->
            render(state)
        }
    }

    private fun render(state: ListsUiState) {
        binding.state = state
        listsAdapter.setItems(state.movieLists)
    }

    override fun onEffect(effect: ListsUiEffect) {
        when (effect) {

            is ListsUiEffect.NavigateToListDetails -> {
                navigator.navigateToMyListDetails(
                    listId = effect.listId,
                    listType = effect.listType,
                    listName = effect.listName
                )
            }

            ListsUiEffect.NavigateBack -> {
                navigator.navigateBack()
            }

            ListsUiEffect.OpenCreateListBottomSheet -> {
                showCreateListBottomSheet()
            }

            is ListsUiEffect.ShowDeleteConfirmation -> {
                showDeleteConfirmation(
                    listId = effect.listId,
                    listName = effect.listName
                )
            }

            is ListsUiEffect.ShowSnackBar -> {
                showSnackBar(effect.message)
            }
        }
    }

    private fun showCreateListBottomSheet() {
        val bottomSheet = CreateListBottomSheetFragment()

        bottomSheet.setListener(this)

        createListBottomSheet.show(
            childFragmentManager,
            "CREATE_LIST_BOTTOM_SHEET"
        )
    }

    private fun showDeleteConfirmation(
        listId: Int,
        listName: String
    ) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(CoreUiR.string.delete)
            .setMessage(
                getString(
                    CoreUiR.string.are_you_sure_that_you_want_to_delete_1d,
                    listName
                )
            )
            .setPositiveButton(CoreUiR.string.confirm) { _, _ ->
                viewModel.deleteList(listId)
            }
            .setNegativeButton(CoreUiR.string.cancel, null)
            .show()
    }

    override fun onClickItem(
        listId: Int,
        listType: String,
        listName: String
    ) {
        viewModel.onEvent(
            ListsUiEvent.ListClicked(
                listId = listId,
                listType = listType,
                listName = listName
            )
        )
    }

    override fun onClickNewList() {
        viewModel.onEvent(ListsUiEvent.NewListClicked)
    }

    override fun onClickBackButton() {
        viewModel.onEvent(
            ListsUiEvent.BackClicked
        )
    }

    override fun onClickTryAgain() {
        viewModel.onEvent(ListsUiEvent.RetryClicked)
    }


    override fun onClickDelete(
        listId: Int,
        listName: String
    ) {
        viewModel.onEvent(
            ListsUiEvent.DeleteClicked(
                listId = listId,
                listName = listName
            )
        )
    }

    override fun onClickCreate(
        listName: String
    ) {
        viewModel.onEvent(
            ListsUiEvent.CreateList(listName)
        )

        createListBottomSheet.dismiss()
    }
}
