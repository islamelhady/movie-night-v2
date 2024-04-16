package com.elhady.movies.presentation.viewmodel.tvdetails.listener

import com.elhady.movies.presentation.viewmodel.common.listener.MediaListener
import com.elhady.movies.presentation.viewmodel.common.listener.PeopleListener
import com.elhady.movies.presentation.viewmodel.common.listener.ChipListener

interface TvDetailsListeners : RateListener, PeopleListener, MediaListener,
    SeasonListener, ShowMoreCast, ShowMoreRecommended, PlayButtonListener, ChipListener