package com.elhady.movies.core.domain.usecase.account

import androidx.paging.PagingData
import com.elhady.movies.core.domain.model.account.MyRatedTvShow
import com.elhady.movies.core.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetMyRatedTVShowsUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(): Flow<PagingData<MyRatedTvShow>> {
        return accountRepository.getRatedTvShows().flow
    }
}
