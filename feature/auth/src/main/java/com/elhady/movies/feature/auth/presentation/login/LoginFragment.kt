package com.elhady.movies.feature.auth.presentation.login

import android.content.Intent
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.fragment.app.viewModels
import com.elhady.movies.feature.auth.BuildConfig
import com.elhady.movies.feature.auth.R
import com.elhady.movies.core.ui.base.BaseFragment
import com.elhady.movies.feature.auth.databinding.FragmentLoginBinding
import com.elhady.movies.core.ui.navigation.Navigator
import com.elhady.movies.core.ui.util.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginUiState, LoginUiEffect>(), LoginListener {

    @Inject
    lateinit var navigator: Navigator

    override val layoutIdFragment = R.layout.fragment_login
    override val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listener = this
        handleKeyboardAppearanceEvent()
    }

    override fun render(state: LoginUiState) {
        binding.state = state
    }

    private fun handleKeyboardAppearanceEvent() {
        with(binding) {

            val globalLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {
                val rect = Rect()
                root.getWindowVisibleDisplayFrame(rect)

                val screenHeight = root.rootView.height
                val keyboardHeight = screenHeight - rect.bottom

                val isKeyboardVisible =
                    keyboardHeight > screenHeight * 0.15

                handleKeyboardAppearanceEvent(isKeyboardVisible)
            }

            root.viewTreeObserver.addOnGlobalLayoutListener(globalLayoutListener)
        }

    }

    private fun handleKeyboardAppearanceEvent(isVisible: Boolean) {
        with(binding.loginMotionLayout) {
            setTransitionDuration(300)
            if (isVisible) {
                transitionToEnd()
            } else {
                transitionToStart()
            }
        }
    }

    override fun onEffect(effect: LoginUiEffect) {
        when (effect) {
            is LoginUiEffect.NavigateToHome -> {
                navigator.navigateToHome()
            }

            is LoginUiEffect.NavigateToSignUp -> {
                val browserIntent =
                    Intent(Intent.ACTION_VIEW, Uri.parse(BuildConfig.TMDB_SIGNUP_URL))
                startActivity(browserIntent)
            }

            is LoginUiEffect.ShowSnackBar -> {
                binding.root.hideKeyboard()
                showSnackBar(effect.message)
            }
        }
    }

    override fun onUsernameChanged(username: String) {
        viewModel.onEvent(LoginUiEvent.UsernameChanged(username))
    }

    override fun onPasswordChanged(password: String) {
        viewModel.onEvent(LoginUiEvent.PasswordChanged(password))
    }

    override fun onLoginClicked() {
        viewModel.onEvent(LoginUiEvent.LoginClicked)
    }

    override fun onSignUpClicked() {
        viewModel.onEvent(LoginUiEvent.SignUpClicked)
    }
}
