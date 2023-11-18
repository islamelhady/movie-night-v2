package com.elhady.movies.ui.favorite

import androidx.fragment.app.activityViewModels
import com.elhady.movies.R
import com.elhady.movies.databinding.DialogCreateListBinding
import com.elhady.movies.ui.base.BaseDialog

class CreateListDialog : BaseDialog<DialogCreateListBinding>() {

    override val layoutIdFragment: Int = R.layout.dialog_create_list
    override val viewModel: FavoriteViewModel by activityViewModels()

}