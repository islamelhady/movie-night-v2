package com.elhady.movies.ui.base

import android.os.Bundle
import android.view.*
import androidx.databinding.*
import androidx.lifecycle.*
import com.elhady.movies.BR
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseFragmentBottomSheet<VDB : ViewDataBinding> : BottomSheetDialogFragment() {

    abstract val layoutIdFragment: Int
    abstract val viewModel: ViewModel

    private lateinit var _binding: VDB
    protected val binding: VDB
        get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutIdFragment, container, false)
        _binding.apply {
            lifecycleOwner = this@BaseFragmentBottomSheet
            setVariable(BR.viewModel, viewModel)
            return root
        }
    }
}