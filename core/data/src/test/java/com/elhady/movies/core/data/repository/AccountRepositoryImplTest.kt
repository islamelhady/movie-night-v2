package com.elhady.movies.core.data.repository

import com.elhady.movies.core.data.mapper.account.UserListsDtoMapper
import com.elhady.movies.core.data.mapper.common.StatusDtoMapper
import com.elhady.movies.core.data.mapper.movie.MovieDtoMapper
import com.elhady.movies.core.data.mapper.movie.MyRatedMoviesDetailsDtoMapper
import com.elhady.movies.core.data.mapper.movie.TvDtoMapper
import com.elhady.movies.core.data.mapper.tvshow.MyRatedTvShowDtoMapper
import com.elhady.movies.core.data.paging.movie.RatedMoviesPagingSource
import com.elhady.movies.core.data.paging.tvshow.RatedTvShowPagingSource
import com.elhady.movies.core.domain.model.common.Status
import com.elhady.movies.core.domain.repository.GenreRepository
import com.elhady.movies.core.network.api.AccountApiService
import com.elhady.movies.core.network.dto.account.AddMediaToListRequest
import com.elhady.movies.core.network.dto.account.ListDetailsWrapperResponse
import com.elhady.movies.core.network.dto.common.StatusResponse
import com.elhady.movies.core.network.dto.movie.MovieDto
import com.elhady.movies.core.network.exception.SafeApiCaller
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class AccountRepositoryImplTest {

    private val accountApiService: AccountApiService = mockk()
    private val genreRepository: GenreRepository = mockk()
    private val movieDtoMapper: MovieDtoMapper = mockk(relaxed = true)
    private val tvDtoMapper: TvDtoMapper = mockk()
    private val domainStatusMapper: StatusDtoMapper = mockk()
    private val myRatedMoviesDetailsDtoMapper: MyRatedMoviesDetailsDtoMapper = mockk()
    private val domainUserListsMapper: UserListsDtoMapper = mockk()
    private val ratedMoviesPagingSource: RatedMoviesPagingSource = mockk()
    private val ratedTvShowPagingSource: RatedTvShowPagingSource = mockk()
    private val myRatedTvShowDtoMapper: MyRatedTvShowDtoMapper = mockk()
    private val safeApiCaller: SafeApiCaller = mockk()

    private lateinit var repository: AccountRepositoryImpl

    @Before
    fun setUp() {
        repository = AccountRepositoryImpl(
            accountApiService, genreRepository, movieDtoMapper,
            tvDtoMapper, domainStatusMapper, myRatedMoviesDetailsDtoMapper,
            domainUserListsMapper, ratedMoviesPagingSource, ratedTvShowPagingSource,
            myRatedTvShowDtoMapper, safeApiCaller
        )
    }

    @Test
    fun `postUserLists should forward correct mediaId and mediaType to API`() = runTest {
        // Given
        val listId = 1
        val mediaId = 100
        val mediaType = "movie"
        val requestSlot = slot<AddMediaToListRequest>()
        
        coEvery { safeApiCaller.execute<StatusResponse>(any()) } coAnswers {
            val call = it.invocation.args[0] as suspend () -> retrofit2.Response<StatusResponse>
            // We just need it to return something, the verification is on the API call within safeApiCaller
            mockk() 
        }
        
        // This is tricky because safeApiCaller.execute wraps the call. 
        // We can mock accountApiService directly if we want to verify the lambda argument passed to execute.
        // But mockk doesn't easily verify lambda contents.
        // Instead, let's mock repository and verify usage if possible, or just mock the API call inside the lambda.
        
        coEvery { accountApiService.postUserMedia(any(), any()) } returns mockk()
        coEvery { domainStatusMapper.map(any<StatusResponse>()) } returns mockk<Status>()

        // When
        repository.postUserLists(listId, mediaId, mediaType)

        // Then
        // Since we can't easily coVerify inside safeApiCaller's lambda from here without more complex setup,
        // we check if postUserLists at least triggers the expected sequence.
        // To be 100% sure of the request body, we'd need to mock safeApiCaller to execute the lambda.
        
        coEvery { safeApiCaller.execute<StatusResponse>(any()) } coAnswers {
            val block = it.invocation.args[0] as suspend () -> retrofit2.Response<StatusResponse>
            block() // Execute the actual API call
            mockk()
        }

        repository.postUserLists(listId, mediaId, mediaType)
        coVerify { accountApiService.postUserMedia(listId, capture(requestSlot)) }
        assertEquals(mediaId, requestSlot.captured.mediaId)
        assertEquals(mediaType, requestSlot.captured.mediaType)
    }

    @Test
    fun `getDetailsList should pass explicit mediaType to movieDtoMapper`() = runTest {
        // Given
        val listId = 5
        val mediaType = "tv"
        val mockResponse = ListDetailsWrapperResponse<MovieDto>(items = listOf(mockk(relaxed = true)))
        
        coEvery { safeApiCaller.execute<ListDetailsWrapperResponse<MovieDto>>(any()) } returns mockResponse
        coEvery { genreRepository.getGenresMovies() } returns emptyList()

        // When
        repository.getDetailsList(listId, mediaType)

        // Then
        coVerify { movieDtoMapper.map(any<MovieDto>(), any(), mediaType = mediaType) }
    }
}
