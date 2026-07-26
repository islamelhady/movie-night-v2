package com.elhady.movies.feature.watchlist.presentation.ui.mylist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.elhady.movies.feature.watchlist.BR
import com.elhady.movies.feature.watchlist.R
import com.elhady.movies.core.ui.R as CoreUiR
import com.elhady.movies.core.common.bases.BaseFragment
import com.elhady.movies.feature.watchlist.databinding.FragmentMyListBinding
import com.elhady.movies.feature.watchlist.presentation.mylist.MyListUiEvent
import com.elhady.movies.feature.watchlist.presentation.mylist.MyListUiState
import com.elhady.movies.feature.watchlist.presentation.mylist.MyListViewModel
import com.elhady.movies.core.common.navigation.Navigator
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyListFragment :
    BaseFragment<FragmentMyListBinding, MyListUiState, MyListUiEvent>(), CreateListener {

    @Inject
    lateinit var navigator: Navigator

    override val layoutIdFragment: Int = R.layout.fragment_my_list
    override val viewModel: MyListViewModel by viewModels()
    override val viewModelVariableId: Int = BR.viewModel
    private lateinit var createListBottomSheet: CreateListBottomSheetFragment

    private lateinit var myListAdapter: MyListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
    }

    private fun setAdapter() {
        myListAdapter = MyListAdapter(mutableListOf(), viewModel)
        binding.recyclerViewMyList.adapter = myListAdapter
    }


    override fun onEvent(event: MyListUiEvent) {
        when (event) {
            is MyListUiEvent.NavigateToListDetails -> {
                navigator.navigateToMyListDetails(
                    event.listId,
                    event.listType,
                    event.listName
                )
            }

            is MyListUiEvent.ApplyCreateList -> {
//                applyCreateList()
            }

            is MyListUiEvent.OpenCreateListBottomSheet -> {
                showBottomSheet()
            }

            is MyListUiEvent.OnClickBack -> {
                navigator.navigateBack()
            }

            is MyListUiEvent.ShowSnackBar -> {
                showSnackBar(event.message)
            }

            is MyListUiEvent.OnCreateNewList -> {
                showSnackBar(event.message)
            }

            is MyListUiEvent.ShowConfirmDeleteDialog -> {
                showDialog(event.listId, event.listName)
            }
        }
    }

    private fun showDialog(listId: Int, listName: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(CoreUiR.string.delete))
            .setMessage(getString(CoreUiR.string.are_you_sure_that_you_want_to_delete_1d,listName))
            .setPositiveButton(getString(CoreUiR.string.confirm)) { _, _ ->
                viewModel.deleteList(listId)
            }
            .setNeutralButton(getString(CoreUiR.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }


    private fun showBottomSheet() {
        createListBottomSheet = CreateListBottomSheetFragment(this)
        createListBottomSheet.show(childFragmentManager, "BOTTOM")
    }

    override fun onClickCreate(listName: String) {
        viewModel.onCreateList(listName)
        createListBottomSheet.dismiss()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getData()
    }
}
