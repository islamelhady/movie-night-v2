package com.elhady.movies.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.elhady.movies.R
import com.elhady.movies.databinding.DialogCreateListBinding
import com.elhady.movies.ui.base.BaseFragmentBottomSheet
import com.elhady.movies.utilities.collectLast

class CreateListDialog : BaseFragmentBottomSheet<DialogCreateListBinding>() {

    override val layoutIdFragment: Int = R.layout.dialog_create_list
    override val viewModel: FavoriteViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        collectLast(viewModel.uiEvent) { event ->
            event?.peekContent()?.let {
                if (it is FavouriteUiEvent.ClickCreateEvent) {
                    dismiss()
                }
            }

        }
    }

}