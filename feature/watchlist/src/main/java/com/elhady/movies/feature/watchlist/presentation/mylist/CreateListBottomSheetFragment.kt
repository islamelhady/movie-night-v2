package com.elhady.movies.feature.watchlist.presentation.mylist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.elhady.movies.feature.watchlist.BR
import com.elhady.movies.feature.watchlist.R
import com.elhady.movies.core.ui.R as CoreUiR
import com.elhady.movies.core.ui.base.BaseBottomSheet
import com.elhady.movies.feature.watchlist.databinding.BottomSheetCreateListBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateListBottomSheetFragment(private val createButton: CreateListener) :
    BaseBottomSheet<BottomSheetCreateListBinding>() {

    override val layoutIdFragment: Int = R.layout.bottom_sheet_create_list
    override val viewModel: MyListViewModel by activityViewModels()
    override val viewModelVariableId: Int = BR.viewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.materialButtonCreate.setOnClickListener {
            val listName = binding.textInputEditTextListName.text.toString().trim()
            if (listName == "") {
                showSnackBar(getString(CoreUiR.string.empty_field))
            } else {
                createButton.onClickCreate(listName)
            }
        }

        binding.textViewClose.setOnClickListener {
            dismiss()
        }
    }

    private fun showSnackBar(messages: String) {
        Snackbar.make(binding.root, messages, Snackbar.LENGTH_SHORT).show()
    }
}

interface CreateListener {
    fun onClickCreate(listName: String)
}
