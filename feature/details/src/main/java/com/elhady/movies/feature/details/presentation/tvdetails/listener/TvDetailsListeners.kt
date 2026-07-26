package com.elhady.movies.feature.details.presentation.tvdetails.listener

import com.elhady.movies.core.ui.listener.MediaListener
import com.elhady.movies.core.ui.listener.PeopleListener
import com.elhady.movies.core.ui.listener.ChipListener

interface TvDetailsListeners : RateListener, PeopleListener, MediaListener,
    SeasonListener, ShowMoreCast, ShowMoreRecommended, PlayButtonListener, ChipListener
