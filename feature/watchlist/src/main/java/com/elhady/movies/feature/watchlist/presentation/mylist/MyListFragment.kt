package com.elhady.movies.feature.watchlist.presentation.mylist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.elhady.movies.core.ui.base.BaseFragment
import com.elhady.movies.core.ui.navigation.Navigator
import com.elhady.movies.feature.watchlist.R
import com.elhady.movies.feature.watchlist.databinding.FragmentMyListBinding
import com.elhady.movies.feature.watchlist.presentation.mylist.adapter.MyListAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.elhady.movies.core.ui.R as CoreUiR

@AndroidEntryPoint
class MyListFragment :
    BaseFragment<FragmentMyListBinding, MyListUiState, MyListUiEffect>(),
    MyListListener, MyListAdapterListener, CreateListener {

    @Inject
    lateinit var navigator: Navigator

    override val layoutIdFragment: Int =
        R.layout.fragment_my_list

    override val viewModel: MyListViewModel by viewModels()

    private val myListAdapter: MyListAdapter by lazy {
        MyListAdapter(
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
        binding.recyclerViewMyList.adapter = myListAdapter
    }

    private fun setupListeners() {
        binding.toolbar.setNavigationOnClickListener {
            viewModel.onEvent(MyListUiEvent.BackClicked)
        }
        binding.listener = this
    }

    private fun collectState() {
        collectFlow(viewModel.state) { state ->
            render(state)
        }
    }

    private fun render(state: MyListUiState) {
        binding.state = state
        myListAdapter.setItems(state.movieLists)
    }

    override fun onEffect(effect: MyListUiEffect) {
        when (effect) {

            is MyListUiEffect.NavigateToListDetails -> {
                navigator.navigateToMyListDetails(
                    listId = effect.listId,
                    listType = effect.listType,
                    listName = effect.listName
                )
            }

            MyListUiEffect.NavigateBack -> {
                navigator.navigateBack()
            }

            MyListUiEffect.OpenCreateListBottomSheet -> {
                showCreateListBottomSheet()
            }

            is MyListUiEffect.ShowDeleteConfirmation -> {
                showDeleteConfirmation(
                    listId = effect.listId,
                    listName = effect.listName
                )
            }

            is MyListUiEffect.ShowSnackBar -> {
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
            MyListUiEvent.ListClicked(
                listId = listId,
                listType = listType,
                listName = listName
            )
        )
    }

    override fun onClickNewList() {
        viewModel.onEvent(MyListUiEvent.NewListClicked)
    }

    override fun onClickBackButton() {
        viewModel.onEvent(
            MyListUiEvent.BackClicked
        )
    }

    override fun onClickTryAgain() {
        viewModel.onEvent(MyListUiEvent.RetryClicked)
    }


    override fun onClickDelete(
        listId: Int,
        listName: String
    ) {
        viewModel.onEvent(
            MyListUiEvent.DeleteClicked(
                listId = listId,
                listName = listName
            )
        )
    }

    override fun onClickCreate(
        listName: String
    ) {
        viewModel.onEvent(
            MyListUiEvent.CreateList(listName)
        )

        createListBottomSheet.dismiss()
    }
}
