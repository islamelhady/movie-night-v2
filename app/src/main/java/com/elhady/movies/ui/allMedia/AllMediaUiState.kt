package com.elhady.movies.ui.allMedia

import androidx.paging.PagingData
import com.elhady.movies.domain.enums.SeeAllType
import com.elhady.movies.ui.models.MediaUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class AllMediaUiState(
    val allMedia: Flow<PagingData<MediaUiState>> = emptyFlow(),
    val mediaType: SeeAllType = SeeAllType.POPULAR_TV,
    val isLoading: Boolean = false,
    val error: List<Error> = emptyList()
)

data class Error(
    val code : Int,
    val message: String,
)