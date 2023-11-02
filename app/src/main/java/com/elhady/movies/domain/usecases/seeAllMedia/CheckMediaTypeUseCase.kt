package com.elhady.movies.domain.usecases.seeAllMedia

import com.elhady.movies.domain.enums.AllMediaType
import javax.inject.Inject

class CheckMediaTypeUseCase @Inject constructor() {
    operator fun invoke(type: AllMediaType): Boolean {
        return (type == AllMediaType.ON_THE_AIR
                || type == AllMediaType.POPULAR
                || type == AllMediaType.TOP_RATED_TV
                || type == AllMediaType.LATEST
                )
    }
}