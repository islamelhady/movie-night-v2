package com.elhady.movies.feature.watchlist.presentation.watchhistory

import com.elhady.movies.core.ui.resource.StringsRes
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.Calendar
import java.util.Date

class WatchHistoryRecyclerItemsCreatorTest {

    private val stringsRes: StringsRes = mockk()
    private lateinit var creator: WatchHistoryRecyclerItemsCreator
    
    // Reference date: 2023-05-15 10:00:00
    private val referenceDate = Calendar.getInstance().apply {
        set(2023, Calendar.MAY, 15, 10, 0, 0)
    }.time

    @Before
    fun setUp() {
        creator = WatchHistoryRecyclerItemsCreator(stringsRes, referenceDate)
        every { stringsRes.today } returns "Today"
        every { stringsRes.yesterday } returns "Yesterday"
    }

    @Test
    fun `composeTitle should return Today when date is same day as reference`() {
        // Given
        val watchedDate = Calendar.getInstance().apply {
            time = referenceDate
            set(Calendar.HOUR_OF_DAY, 20) // Different time, same day
        }.time
        
        val movie = MovieUiState(1, "", "Title", "", 2023, 8.0, watchedDate)

        // When
        val items = creator.createItems(listOf(movie))

        // Then
        val titleItem = items.first { it is WatchHistoryRecyclerItem.Title } as WatchHistoryRecyclerItem.Title
        assertEquals("Today", titleItem.title)
    }

    @Test
    fun `composeTitle should return Yesterday when date is previous day`() {
        // Given
        val watchedDate = Calendar.getInstance().apply {
            time = referenceDate
            add(Calendar.DAY_OF_YEAR, -1)
        }.time
        
        val movie = MovieUiState(1, "", "Title", "", 2023, 8.0, watchedDate)

        // When
        val items = creator.createItems(listOf(movie))

        // Then
        val titleItem = items.first { it is WatchHistoryRecyclerItem.Title } as WatchHistoryRecyclerItem.Title
        assertEquals("Yesterday", titleItem.title)
    }

    @Test
    fun `composeTitle should return Yesterday across month boundary`() {
        // Reference: June 1st
        val ref = Calendar.getInstance().apply { 
            set(Calendar.YEAR, 2023)
            set(Calendar.MONTH, Calendar.JUNE)
            set(Calendar.DAY_OF_MONTH, 1)
        }.time
        val creatorBoundary = WatchHistoryRecyclerItemsCreator(stringsRes, ref)
        
        // Watched: May 31st
        val watchedDate = Calendar.getInstance().apply {
            set(2023, Calendar.MAY, 31, 20, 0, 0)
        }.time
        
        val movie = MovieUiState(1, "", "Title", "", 2023, 8.0, watchedDate)

        // When
        val items = creatorBoundary.createItems(listOf(movie))

        // Then
        val titleItem = items.first { it is WatchHistoryRecyclerItem.Title } as WatchHistoryRecyclerItem.Title
        assertEquals("Yesterday", titleItem.title)
    }

    @Test
    fun `composeTitle should return Yesterday across year boundary`() {
        // Reference: Jan 1st 2024
        val ref = Calendar.getInstance().apply { 
            set(Calendar.YEAR, 2024)
            set(Calendar.MONTH, Calendar.JANUARY)
            set(Calendar.DAY_OF_MONTH, 1)
        }.time
        val creatorBoundary = WatchHistoryRecyclerItemsCreator(stringsRes, ref)
        
        // Watched: Dec 31st 2023
        val watchedDate = Calendar.getInstance().apply {
            set(2023, Calendar.DECEMBER, 31, 20, 0, 0)
        }.time
        
        val movie = MovieUiState(1, "", "Title", "", 2023, 8.0, watchedDate)

        // When
        val items = creatorBoundary.createItems(listOf(movie))

        // Then
        val titleItem = items.first { it is WatchHistoryRecyclerItem.Title } as WatchHistoryRecyclerItem.Title
        assertEquals("Yesterday", titleItem.title)
    }
}
