package com.elhady.movies.core.domain.usecase.watchlist.myrated

import androidx.paging.PagingData
import com.elhady.movies.core.domain.model.myrated.MyRatedMovieEntity
import com.elhady.movies.core.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetMyRatedMoviesUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(): Flow<PagingData<MyRatedMovieEntity>> {
        return accountRepository.getRatedMovies().flow
    }
}
