package com.elhady.movies.feature.watchlist.presentation.lists

import android.os.Bundle
import android.view.View
import com.elhady.movies.feature.watchlist.R
import com.elhady.movies.core.ui.R as CoreUiR
import com.elhady.movies.core.ui.base.BaseBottomSheet
import com.elhady.movies.feature.watchlist.databinding.BottomSheetCreateListBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateListBottomSheetFragment : BaseBottomSheet<BottomSheetCreateListBinding>() {

    override val layoutIdFragment: Int = R.layout.bottom_sheet_create_list
    private var listener: CreateListener? = null
    fun setListener(listener: CreateListener) {
        this.listener = listener
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        binding.materialButtonCreate.setOnClickListener {
            val listName = binding.editTextListName.text
                .toString()
                .trim()

            if (listName.isEmpty()) {
                showSnackBar(
                    getString(CoreUiR.string.empty_field)
                )
                return@setOnClickListener
            }

            listener?.onClickCreate(listName)
            dismiss()
        }

        binding.textViewClose.setOnClickListener {
            dismiss()
        }
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(
            binding.root,
            message,
            Snackbar.LENGTH_SHORT
        ).show()
    }
}
