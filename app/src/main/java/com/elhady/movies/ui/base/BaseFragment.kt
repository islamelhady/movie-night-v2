package com.elhady.movies.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.elhady.movies.BR
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

abstract class BaseFragment<VDB : ViewDataBinding, STATE, EVENT> : Fragment() {

    abstract val layoutIdFragment: Int

    abstract val viewModel: BaseViewModel<STATE, EVENT>

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
            setVariable(BR.viewModel, viewModel)
            return root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectLatest {
            viewModel.event.collectLatest {
                onEvent(it)
            }
        }
    }

    private fun collectLatest(collect: suspend CoroutineScope.() -> Unit) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                collect()
            }
        }
    }
    abstract fun onEvent(event: EVENT)

    protected fun setTitle(visibility: Boolean, title: String? = null) {
        if (visibility) {
            (activity as AppCompatActivity).supportActionBar?.show()
            title?.let {
                (activity as AppCompatActivity).supportActionBar?.title = it
            }
        } else {
            (activity as AppCompatActivity).supportActionBar?.hide()
        }
    }

    protected fun showSnackBar(message: String){
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }
}