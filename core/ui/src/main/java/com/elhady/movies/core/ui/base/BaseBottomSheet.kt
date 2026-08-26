package com.elhady.movies.core.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * A base class for all BottomSheetDialog fragments in the project that use Data Binding.
 *
 * @param VDB The type of the ViewDataBinding associated with the fragment's layout.
 */
abstract class BaseBottomSheet<VDB : ViewDataBinding>
    : BottomSheetDialogFragment() {

    @get:LayoutRes
    abstract val layoutIdFragment: Int
    protected open val viewModel: ViewModel? = null

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
            lifecycleOwner = viewLifecycleOwner
            return root
        }
    }

    protected fun <T> collectFlow(flow: Flow<T>, collect: suspend (T) -> Unit) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                flow.collectLatest { collect(it) }
            }
        }
    }
}
