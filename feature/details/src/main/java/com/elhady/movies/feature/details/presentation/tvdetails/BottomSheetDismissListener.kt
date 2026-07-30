package com.elhady.movies.feature.details.presentation.tvdetails

interface BottomSheetDismissListener {
    fun onApplyRateBottomSheet()
    fun updateRatingValue(rate: Float)
    fun getUserRating(): Float
}
