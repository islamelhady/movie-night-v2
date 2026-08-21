package com.elhady.movies.feature.details.presentation.tvdetails.listener

interface BottomSheetDismissListener {
    fun onApplyRateBottomSheet()
    fun updateRatingValue(rate: Float)
    fun getUserRating(): Float
}