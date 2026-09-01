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

    private var globalLayoutListener: ViewTreeObserver.OnGlobalLayoutListener? = null

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

    override fun onDestroyView() {
        _binding?.root?.viewTreeObserver?.removeOnGlobalLayoutListener(globalLayoutListener)
        globalLayoutListener = null
        super.onDestroyView()
    }

    private fun handleKeyboardAppearanceEvent() {
        globalLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {
            _binding?.let { binding ->
                val rect = Rect()
                binding.root.getWindowVisibleDisplayFrame(rect)

                val screenHeight = binding.root.rootView.height
                val keyboardHeight = screenHeight - rect.bottom

                val isKeyboardVisible = keyboardHeight > screenHeight * 0.15

                handleKeyboardAppearanceEvent(isKeyboardVisible)
            }
        }
        binding.root.viewTreeObserver.addOnGlobalLayoutListener(globalLayoutListener)
    }

    private fun handleKeyboardAppearanceEvent(isVisible: Boolean) {
        _binding?.loginMotionLayout?.let {
            it.setTransitionDuration(300)
            if (isVisible) {
                it.transitionToEnd()
            } else {
                it.transitionToStart()
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
                _binding?.root?.hideKeyboard()
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
