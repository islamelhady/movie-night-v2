package com.elhady.movies.core.data.repository

import com.elhady.movies.core.common.MediaType
import com.elhady.movies.core.data.mapper.account.CreateListDtoMapper
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
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import javax.inject.Provider

class AccountRepositoryImplTest {

    private val accountApiService: AccountApiService = mockk()
    private val genreRepository: GenreRepository = mockk()
    private val movieDtoMapper: MovieDtoMapper = mockk(relaxed = true)
    private val tvDtoMapper: TvDtoMapper = mockk()
    private val domainStatusMapper: StatusDtoMapper = mockk()
    private val myRatedMoviesDetailsDtoMapper: MyRatedMoviesDetailsDtoMapper = mockk()
    private val domainUserListsMapper: UserListsDtoMapper = mockk()
    private val createListDtoMapper: CreateListDtoMapper = mockk()
    private val ratedMoviesPagingSourceProvider: Provider<RatedMoviesPagingSource> = mockk()
    private val ratedTvShowPagingSourceProvider: Provider<RatedTvShowPagingSource> = mockk()
    private val myRatedTvShowDtoMapper: MyRatedTvShowDtoMapper = mockk()
    private val safeApiCaller: SafeApiCaller = mockk()

    private lateinit var repository: AccountRepositoryImpl

    @Before
    fun setUp() {
        repository = AccountRepositoryImpl(
            accountApiService, genreRepository, movieDtoMapper,
            tvDtoMapper, domainStatusMapper, myRatedMoviesDetailsDtoMapper,
            domainUserListsMapper, createListDtoMapper, ratedMoviesPagingSourceProvider,
            ratedTvShowPagingSourceProvider, myRatedTvShowDtoMapper, safeApiCaller
        )
    }

    @Test
    fun `postUserLists should forward correct mediaId and mediaType to API`() = runTest {
        // Given
        val listId = 1
        val mediaId = 100
        val mediaType = MediaType.MOVIE
        val requestSlot = slot<AddMediaToListRequest>()
        
        coEvery { safeApiCaller.execute<StatusResponse>(any()) } coAnswers {
            val block = it.invocation.args[0] as suspend () -> retrofit2.Response<StatusResponse>
            block() 
            mockk()
        }
        
        coEvery { accountApiService.postUserMedia(any(), any()) } returns mockk()
        coEvery { domainStatusMapper.map(any<StatusResponse>()) } returns mockk<Status>()

        // When
        repository.postUserLists(listId, mediaId, mediaType)

        // Then
        coVerify { accountApiService.postUserMedia(listId, capture(requestSlot)) }
        assertEquals(mediaId, requestSlot.captured.mediaId)
        assertEquals(mediaType.value, requestSlot.captured.mediaType)
    }

    @Test
    fun `createRatedMoviesPagingSource should return a NEW instance from provider on every call`() {
        // Given
        val mockPagingSource1: RatedMoviesPagingSource = mockk()
        val mockPagingSource2: RatedMoviesPagingSource = mockk()
        every { ratedMoviesPagingSourceProvider.get() } returns mockPagingSource1 andThen mockPagingSource2

        // When
        val instance1 = repository.createRatedMoviesPagingSource()
        val instance2 = repository.createRatedMoviesPagingSource()

        // Then
        coVerify(exactly = 2) { ratedMoviesPagingSourceProvider.get() }
        assertNotEquals(instance1, instance2)
    }

    @Test
    fun `getDetailsList should pass explicit mediaType to movieDtoMapper`() = runTest {
        // Given
        val listId = 5
        val mediaType = MediaType.TV_SHOW
        val mockResponse = ListDetailsWrapperResponse<MovieDto>(items = listOf(mockk(relaxed = true)))
        
        coEvery { safeApiCaller.execute<ListDetailsWrapperResponse<MovieDto>>(any()) } returns mockResponse
        coEvery { genreRepository.getGenresMovies() } returns emptyList()

        // When
        repository.getDetailsList(listId, mediaType)

        // Then
        coVerify { movieDtoMapper.map(any<MovieDto>(), any(), mediaType = mediaType) }
    }
}
