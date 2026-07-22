package com.elhady.movies.feature.details.presentation.ui.youtubeplayer

import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.elhady.movies.feature.details.BR
import androidx.fragment.app.viewModels
import com.elhady.movies.feature.details.R
import com.elhady.movies.core.common.bases.BaseFragment
import com.elhady.movies.feature.details.databinding.FragmentTrailerBinding
import com.elhady.movies.feature.details.presentation.youtubeplayer.TrailerInteraction
import com.elhady.movies.feature.details.presentation.youtubeplayer.YoutubePlayerUIState
import com.elhady.movies.feature.details.presentation.youtubeplayer.TrailerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrailerFragment :
    BaseFragment<FragmentTrailerBinding, YoutubePlayerUIState, TrailerInteraction>() {

    override val layoutIdFragment = R.layout.fragment_trailer
    override val viewModel: TrailerViewModel by viewModels()
    override val viewModelVariableId: Int = BR.viewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        activity?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        activity?.window?.navigationBarColor = Color.BLACK
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            activity?.window?.setDecorFitsSystemWindows(false)
        } else {
            activity?.window?.decorView?.systemUiVisibility =
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }

    override fun onEvent(event: TrailerInteraction) {

    }
}
