package com.elhady.movies.presentation.viewmodel.tvdetails.listener

import com.elhady.movies.core.common.presentation.MediaListener
import com.elhady.movies.core.common.presentation.PeopleListener
import com.elhady.movies.core.common.presentation.ChipListener

interface TvDetailsListeners : RateListener, PeopleListener, MediaListener,
    SeasonListener, ShowMoreCast, ShowMoreRecommended, PlayButtonListener, ChipListener
