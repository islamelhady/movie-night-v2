package com.elhady.movies.feature.player.presentation.player

import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.elhady.movies.core.ui.base.BaseFragment
import com.elhady.movies.core.ui.navigation.Navigator
import com.elhady.movies.feature.player.R
import com.elhady.movies.feature.player.databinding.FragmentPlayerBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PlayerFragment :
    BaseFragment<FragmentPlayerBinding, PlayerUiState, PlayerUiEffect>() {

    @Inject
    lateinit var navigator: Navigator

    override val layoutIdFragment = R.layout.fragment_player
    override val viewModel: PlayerViewModel by viewModels()

    private var youtubePlayer: YouTubePlayer? = null
    private var isVideoLoaded = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycle.addObserver(binding.youtubePlayer)
        setupSystemBars()
        setupYoutubePlayer()
        setListeners()
    }

    private fun setupSystemBars() {
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        activity?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        activity?.window?.navigationBarColor = Color.BLACK
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            activity?.window?.setDecorFitsSystemWindows(false)
        } else {
            @Suppress("DEPRECATION")
            activity?.window?.decorView?.systemUiVisibility =
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
        }
    }

    private fun setupYoutubePlayer() {
        binding.youtubePlayer.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                this@PlayerFragment.youtubePlayer = youTubePlayer
                render(viewModel.state.value)
            }
        })
    }

    override fun render(state: PlayerUiState) {
        if (youtubePlayer != null && state.videoKey.isNotEmpty() && !isVideoLoaded) {
            youtubePlayer?.cueVideo(state.videoKey, 0f)
            isVideoLoaded = true
        }

        binding.lottieAnimation.isVisible = state.errors != null
        state.errors?.let {
            binding.lottieAnimation.setAnimation(it.animationRes)
            binding.lottieAnimation.playAnimation()
        }
    }

    private fun setListeners() {
        binding.buttonBack.setOnClickListener {
            viewModel.onEvent(PlayerUiEvent.BackClicked)
        }
    }

    override fun onEffect(effect: PlayerUiEffect) {
        when (effect) {
            PlayerUiEffect.NavigateBack -> navigator.navigateBack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }
}
