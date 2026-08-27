package com.elhady.movies.core.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * A base class for all fragments in the project that use Data Binding.
 *
 * @param VDB The type of the ViewDataBinding associated with the fragment's layout.
 * @param STATE The type of the UI state associated with the fragment's ViewModel.
 * @param EFFECT The type of the UI effect associated with the fragment's ViewModel.
 */
abstract class BaseFragment<VDB : ViewDataBinding, STATE, EFFECT> : Fragment() {
    @get:LayoutRes
    abstract val layoutIdFragment: Int

    abstract val viewModel: BaseViewModel<STATE, EFFECT>

    protected var _binding: VDB? = null
    protected val binding: VDB
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutIdFragment, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding?.lifecycleOwner = viewLifecycleOwner
        collectFlow(viewModel.effect) { onEffect(it) }
        collectFlow(viewModel.state) { render(it) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Collects a [Flow] in a lifecycle-aware manner.
     *
     * @param T The type of data in the flow.
     * @param flow The flow to be collected.
     * @param collect The action to perform when a new value is emitted.
     */
    protected fun <T> collectFlow(flow: Flow<T>, collect: suspend (T) -> Unit) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                flow.collectLatest { collect(it) }
            }
        }
    }

    /**
     * Called when a UI effect is emitted by the [viewModel].
     *
     * @param effect The emitted UI effect.
     */
    abstract fun onEffect(effect: EFFECT)

    abstract fun render(state: STATE)

    protected fun showSnackBar(messages: String) {
        Snackbar.make(binding.root, messages, Snackbar.LENGTH_SHORT).show()
    }
}
