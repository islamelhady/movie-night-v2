package com.elhady.movies.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.elhady.movies.BR
import com.elhady.movies.utilities.collectLast
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment<VDB: ViewDataBinding, STATE, EVENT>: Fragment(){

    abstract val layoutIdFragment: Int

    //    lateinit var viewModel: VM
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
        collectLast(flow = viewModel.event, action = {onEvent(it)})
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