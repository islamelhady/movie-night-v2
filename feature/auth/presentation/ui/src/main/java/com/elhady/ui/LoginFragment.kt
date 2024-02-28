package com.elhady.ui

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.elhady.base.BaseFragment
import com.elhady.ui.databinding.FragmentLoginBinding
import com.elhady.viewmodel.LoginUiEvent
import com.elhady.viewmodel.LoginUiState
import com.elhady.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginUiState, LoginUiEvent>() {
    override val layoutIdFragment: Int = R.layout.fragment_login
    override val viewModel: LoginViewModel by viewModels()


    override fun onEvent(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.LoginEvent -> {
                val navController = findNavController()
                navController.popBackStack(navController.graph.startDestinationId, inclusive = false)
                navController.navigate(event.login)
            }
            LoginUiEvent.SignUpEvent -> {
                val browser = Intent(Intent.ACTION_VIEW, Uri.parse(TMDB_SIGNUP_URL))
                startActivity(browser)
            }

            is LoginUiEvent.ShowSnackBar -> showSnackBar(event.message)
        }
    }
    companion object {
        const val TMDB_SIGNUP_URL = "https://www.themoviedb.org/signup"

    }
}