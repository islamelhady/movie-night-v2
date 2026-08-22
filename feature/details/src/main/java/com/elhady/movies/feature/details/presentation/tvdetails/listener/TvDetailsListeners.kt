package com.elhady.movies.feature.details.presentation.tvdetails.listener

import com.elhady.movies.core.ui.interaction.MediaListener
import com.elhady.movies.core.ui.interaction.PeopleListener
import com.elhady.movies.core.ui.interaction.ChipListener

interface TvDetailsListeners : RateListener, PeopleListener, MediaListener,
    SeasonListener, ShowMoreCast, ShowMoreRecommended, PlayButtonListener, ChipListener {
    fun onClickBack()
    fun onSaveClicked()
    fun onClickTryAgain()
}
