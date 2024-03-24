package com.elhady.movies.presentation.ui.mylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.elhady.movies.BR
import com.elhady.movies.R
import com.elhady.movies.databinding.BottomSheetCreateListBinding
import com.elhady.movies.presentation.viewmodel.mylist.MyListViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateListBottomSheetFragment(private val createButton: CreateListener) :
    BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetCreateListBinding
    val viewModel by activityViewModels<MyListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_create_list, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            setVariable(BR.viewModel, viewModel)
            return root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.materialButtonCreate.setOnClickListener {
            val listName = binding.textInputEditTextListName.text.toString().trim()
            if (listName == "") {
                showSnackBar(getString(R.string.empty_field))
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