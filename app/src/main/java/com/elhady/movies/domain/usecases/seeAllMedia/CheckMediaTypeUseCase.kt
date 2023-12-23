package com.elhady.movies.domain.usecases.seeAllMedia

import com.elhady.movies.domain.enums.SeeAllType
import javax.inject.Inject

class CheckMediaTypeUseCase @Inject constructor() {
    operator fun invoke(type: SeeAllType): Boolean {
        return (type == SeeAllType.ON_THE_AIR_TV
                || type == SeeAllType.POPULAR_TV
                || type == SeeAllType.TOP_RATED_TV
                || type == SeeAllType.LATEST_TV
                )
    }
}