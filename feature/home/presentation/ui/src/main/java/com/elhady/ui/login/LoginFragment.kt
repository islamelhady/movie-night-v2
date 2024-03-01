package com.elhady.ui.login

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.elhady.base.BaseFragment
import com.elhady.ui.R
import com.elhady.ui.databinding.FragmentLoginBinding
import com.elhady.viewmodel.login.LoginUiEvent
import com.elhady.viewmodel.login.LoginUiState
import com.elhady.viewmodel.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginUiState, LoginUiEvent>() {
    override val layoutIdFragment: Int = R.layout.fragment_login
    override val viewModel: LoginViewModel by viewModels()

    override fun onStart() {
        super.onStart()
        collectLast(viewModel.event) {
             onEvent(it)
        }
    }

    override fun onEvent(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.LoginEvent -> {
                val navController = findNavController()
                navController.popBackStack(navController.graph.startDestinationId, inclusive = false)
                navController.navigate(event.login)
            }
            LoginUiEvent.SignUpEvent -> {
                val browser = Intent(Intent.ACTION_VIEW, Uri.parse(Constants.TMDB_SIGNUP_URL))
                startActivity(browser)
            }

            is LoginUiEvent.ShowSnackBar -> showSnackBar(event.message)
        }
    }

}