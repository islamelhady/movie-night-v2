package com.elhady.movies.core.data.mapper.movie

import com.elhady.movies.core.common.MediaType
import com.elhady.movies.core.common.toMediaType
import com.elhady.movies.core.data.BuildConfig
import com.elhady.movies.core.common.Mapper
import com.elhady.movies.core.network.dto.movie.CreditsDto
import com.elhady.movies.core.network.dto.movie.MovieDetailsDto
import com.elhady.movies.core.network.dto.movie.RecommendationsDto
import com.elhady.movies.core.network.dto.movie.ReviewsDto
import com.elhady.movies.core.network.dto.movie.VideosDto
import com.elhady.movies.core.domain.model.movie.Cast
import com.elhady.movies.core.domain.model.movie.Credits
import com.elhady.movies.core.domain.model.movie.Crew
import com.elhady.movies.core.domain.model.movie.MovieDetails
import com.elhady.movies.core.domain.model.movie.MovieVideo
import com.elhady.movies.core.domain.model.movie.Recommendations
import com.elhady.movies.core.domain.model.movie.RecommendedMovie
import com.elhady.movies.core.domain.model.common.Review
import com.elhady.movies.core.domain.model.movie.ReviewResponse
import com.elhady.movies.core.domain.model.movie.Videos
import com.elhady.movies.core.data.mapper.common.AccountStatesDtoMapper
import javax.inject.Inject

class MovieDetailsDtoMapper @Inject constructor(
    private val accountStatesDtoMapper: AccountStatesDtoMapper
) : Mapper<MovieDetailsDto, MovieDetails> {
    override fun map(input: MovieDetailsDto): MovieDetails {
        return MovieDetails(
            backdropPath = BuildConfig.IMAGE_BASE_PATH + input.backdropPath,
            credits = mapCredits(input.credits),
            genres = input.genres?.mapNotNull { it.name } ?: emptyList(),
            id = input.id ?: 0,
            overview = input.overview ?: "",
            recommendations = mapRecommendations(input.recommendations),
            title = input.title ?: "",
            video = input.video ?: false,
            videos = mapVideos(input.videos),
            voteAverage = input.voteAverage ?: 0.0,
            reviewEntity = mapReviews(input.reviews),
            year = input.releaseDate ?: "",
            accountStates = accountStatesDtoMapper.map(input.accountStates)
        )
    }

    private fun mapCredits(credits: CreditsDto?): Credits {
        return Credits(
            cast = credits?.cast?.map {
                Cast(
                    adult = it.adult ?: false,
                    castId = it.castId ?: 0,
                    character = it.character ?: "",
                    creditId = it.creditId ?: "",
                    gender = it.gender ?: 0,
                    id = it.id ?: 0,
                    knownForDepartment = it.knownForDepartment ?: "",
                    name = it.name ?: "",
                    order = it.order ?: 0,
                    originalName = it.originalName ?: "",
                    popularity = it.popularity ?: 0.0,
                    profilePath = BuildConfig.IMAGE_BASE_PATH + it.profilePath
                )
            } ?: emptyList(),
            crew = credits?.crew?.map {
                Crew(
                    adult = it.adult ?: false,
                    creditId = it.creditId ?: "",
                    department = it.department ?: "",
                    gender = it.gender ?: 0,
                    id = it.id ?: 0,
                    knownForDepartment = it.knownForDepartment ?: "",
                    name = it.name ?: "",
                    job = it.job ?: "",
                    originalName = it.originalName ?: "",
                    popularity = it.popularity ?: 0.0,
                    profilePath = it.profilePath ?: ""
                )
            } ?: emptyList(),
        )
    }

    private fun mapRecommendations(recommendations: RecommendationsDto?): Recommendations {
        return Recommendations(
            page = recommendations?.page ?: 0,
            recommendedMovies = recommendations?.recommendedMovies?.map {
                RecommendedMovie(
                    adult = it.adult ?: false,
                    backdropPath = BuildConfig.IMAGE_BASE_PATH + it.backdropPath,
                    genreIds = it.genreIds ?: emptyList(),
                    id = it.id ?: 0,
                    mediaType = it.mediaType.toMediaType(),
                    originalLanguage = it.originalLanguage ?: "",
                    originalTitle = it.originalTitle ?: "",
                    overview = it.overview ?: "",
                    popularity = it.popularity ?: 0.0,
                    posterPath = BuildConfig.IMAGE_BASE_PATH + it.posterPath,
                    releaseDate = it.releaseDate ?: "",
                    title = it.title ?: "",
                    video = it.video ?: false,
                    voteAverage = it.voteAverage ?: 0.0,
                    voteCount = it.voteCount ?: 0,
                )
            } ?: emptyList(),
            totalPages = recommendations?.totalPages ?: 0,
            totalResults = recommendations?.totalResults ?: 0
        )
    }

    private fun mapVideos(videos: VideosDto?): Videos {
        return Videos(
            results = videos?.results?.map {
                MovieVideo(
                    id = it.id ?: "",
                    iso31661 = it.iso31661 ?: "",
                    iso6391 = it.iso6391 ?: "",
                    key = it.key ?: "",
                    name = it.name ?: "",
                    official = it.official ?: false,
                    publishedAt = it.publishedAt ?: "",
                    site = it.site ?: "",
                    size = it.size ?: 0,
                    type = it.type ?: ""
                )
            } ?: emptyList()
        )
    }

    private fun mapReviews(reviews: ReviewsDto?): ReviewResponse {
        return ReviewResponse(
            reviews = reviews?.results?.map {
                Review(
                    name = it.author ?: "",
                    avatarPath = it.authorDetails?.avatarPath ?: "",
                    content = it.content ?: "",
                    createdAt = it.createdAt ?: ""
                )
            } ?: emptyList(),
            page = reviews?.page ?: 0,
            totalResults = reviews?.totalResults ?: 0,
            totalPages = reviews?.totalPages ?: 0

        )
    }
}
