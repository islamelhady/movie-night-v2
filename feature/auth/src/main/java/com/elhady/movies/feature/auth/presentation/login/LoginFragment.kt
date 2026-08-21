package com.elhady.movies.feature.auth.presentation.login

import android.content.Intent
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.elhady.movies.feature.auth.BuildConfig
import com.elhady.movies.feature.auth.BR
import com.elhady.movies.feature.auth.R
import com.elhady.movies.core.ui.base.BaseFragment
import com.elhady.movies.feature.auth.databinding.FragmentLoginBinding
import com.elhady.movies.core.ui.navigation.Navigator
import com.elhady.movies.core.ui.util.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginUiState, LoginUiEffect>() {

    @Inject
    lateinit var navigator: Navigator

    override val layoutIdFragment = R.layout.fragment_login
    override val viewModel: LoginViewModel by viewModels()
    override val viewModelVariableId: Int = BR.viewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectState()
        setListeners()
        handleKeyboardAppearanceEvent()
    }

    private fun setListeners() {
        binding.edittextUsername.editText?.addTextChangedListener {
            viewModel.onEvent(LoginUiEvent.UsernameChanged(it.toString()))
        }
        binding.edittextPassword.editText?.addTextChangedListener {
            viewModel.onEvent(LoginUiEvent.PasswordChanged(it.toString()))
        }
        binding.buttonLogin.setOnClickListener {
            viewModel.onEvent(LoginUiEvent.LoginClicked)
        }
        binding.textviewSignup.setOnClickListener {
            viewModel.onEvent(LoginUiEvent.SignUpClicked)
        }
    }

    private fun collectState() {
        collectFlow(viewModel.state) { render(it) }
    }

    private fun render(state: LoginUiState) {
        binding.progressBar.isVisible = state.isLoading
        binding.buttonLogin.isVisible = !state.isLoading

        binding.edittextUsername.error = state.usernameError
        binding.edittextPassword.error = state.passwordError
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
}
