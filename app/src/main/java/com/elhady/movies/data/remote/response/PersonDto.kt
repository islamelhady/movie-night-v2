package com.elhady.movies.data.remote.response


import com.google.gson.annotations.SerializedName

data class PersonDto(
    @SerializedName("adult")
    val adult: Boolean?,
    @SerializedName("gender")
    val gender: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("known_for_department")
    val knownForDepartment: String?,
    @SerializedName("media_type")
    val mediaType: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("original_name")
    val originalName: String?,
    @SerializedName("popularity")
    val popularity: Double?,
    @SerializedName("profile_path")
    val profilePath: String?,
    @SerializedName("also_known_as")
    val alsoKnownAs: List<String?>?,
    @SerializedName("biography")
    val biography: String?,
    @SerializedName("birthday")
    val birthday: String?,
    @SerializedName("deathday")
    val deathday: Any?,
    @SerializedName("homepage")
    val homepage: Any?,
    @SerializedName("imdb_id")
    val imdbId: String?,
    @SerializedName("place_of_birth")
    val placeOfBirth: String?,

)