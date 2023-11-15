package com.elhady.movies.ui.login

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.elhady.movies.R
import com.elhady.movies.databinding.FragmentLoginBinding
import com.elhady.movies.ui.base.BaseFragment
import com.elhady.movies.utilities.Constants
import com.elhady.movies.utilities.collectLast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    override val layoutIdFragment: Int = R.layout.fragment_login
    override val viewModel: LoginViewModel by viewModels()

    override fun onStart() {
        super.onStart()
        collectLast(viewModel.loginUiEvent) {
            it.getContentIfNotHandled()?.let { onEvent(it) }
        }
    }

    private fun onEvent(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.LoginEvent -> {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
            }
            LoginUiEvent.SignUpEvent -> {
                val browser = Intent(Intent.ACTION_VIEW, Uri.parse(Constants.TMDB_SIGNUP_URL))
                startActivity(browser)
            }
        }
    }

}