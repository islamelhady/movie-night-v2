package com.elhady.movies.ui.login

import androidx.fragment.app.viewModels
import com.elhady.movies.R
import com.elhady.movies.databinding.FragmentLoginBinding
import com.elhady.movies.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    override val layoutIdFragment: Int = R.layout.fragment_login
    override val viewModel: LoginViewModel by viewModels()


}