package com.elhady.movies.domain.usecases.seeAllMedia

import com.elhady.movies.domain.enums.AllMediaType
import javax.inject.Inject

class CheckMediaTypeUseCase @Inject constructor() {
    operator fun invoke(type: AllMediaType): Boolean {
        return (type == AllMediaType.UPCOMING || type == AllMediaType.TRENDING || type == AllMediaType.NOW_PLAYING || type == AllMediaType.MYSTERY
                || type == AllMediaType.ADVENTURE || type == AllMediaType.LATEST || type == AllMediaType.POPULAR || type == AllMediaType.TOP_RATED_TV
                || type == AllMediaType.TOP_RATED_MOVIE || type == AllMediaType.ON_THE_AIR)
    }
}