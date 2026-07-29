package com.elhady.movies.core.domain.model.tvshow

import com.elhady.movies.core.domain.model.common.Genre

data class Tv(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val genreEntities: List<Genre>,
    val rate: Double,
    val year: String,
){
    fun convertGenreListToString(): String {
        return genreEntities.joinToString(" | ") { it.genreName }
    }

    fun extractYearFromDate(): String {
        val parts = year.split("-")
        return parts[0]
    }
}
