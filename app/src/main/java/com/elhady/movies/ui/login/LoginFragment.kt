package com.elhady.movies.ui.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.elhady.movies.R
import com.elhady.movies.databinding.FragmentLoginBinding
import com.elhady.movies.ui.base.BaseFragment
import com.elhady.movies.utilities.EventObserve
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    override val layoutIdFragment: Int = R.layout.fragment_login
    override val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    }

}