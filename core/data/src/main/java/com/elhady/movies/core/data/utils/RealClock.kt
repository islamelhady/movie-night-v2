package com.elhady.movies.core.data.utils

import com.elhady.movies.core.domain.utils.Clock
import javax.inject.Inject

class RealClock @Inject constructor() : Clock {
    override fun now(): Long = System.currentTimeMillis()
}
