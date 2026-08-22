package com.elhady.movies.feature.details.presentation.tvdetails

import androidx.lifecycle.SavedStateHandle
import com.elhady.movies.core.common.AppException
import com.elhady.movies.core.domain.model.account.UserList
import com.elhady.movies.core.domain.model.common.Review
import com.elhady.movies.core.domain.model.common.YoutubeVideoDetails
import com.elhady.movies.core.domain.model.people.People
import com.elhady.movies.core.domain.model.tvshow.Season
import com.elhady.movies.core.domain.model.tvshow.TvDetailsInfo
import com.elhady.movies.core.domain.model.tvshow.TvShow
import com.elhady.movies.core.domain.usecase.account.AddToFavouriteUseCase
import com.elhady.movies.core.domain.usecase.account.AddToUserListUseCase
import com.elhady.movies.core.domain.usecase.account.AddToWatchList
import com.elhady.movies.core.domain.usecase.account.CreateUserListUseCase
import com.elhady.movies.core.domain.usecase.account.GetUserListsUseCase
import com.elhady.movies.core.domain.usecase.auth.CheckIsUserLoggedInUseCase
import com.elhady.movies.core.domain.usecase.tvshow.GetRatingTvUseCase
import com.elhady.movies.core.domain.usecase.tvshow.GetTvDetailsCastUseCase
import com.elhady.movies.core.domain.usecase.tvshow.GetTvDetailsInfoUseCase
import com.elhady.movies.core.domain.usecase.tvshow.GetTvDetailsReviewsUseCase
import com.elhady.movies.core.domain.usecase.tvshow.GetTvDetailsSeasonsUseCase
import com.elhady.movies.core.domain.usecase.tvshow.GetTvShowRecommendationsUseCase
import com.elhady.movies.core.domain.usecase.tvshow.GetTvShowYoutubeDetailsUseCase
import com.elhady.movies.core.domain.usecase.tvshow.RateTvShowUseCase
import com.elhady.movies.core.ui.base.BaseViewModel
import com.elhady.movies.core.ui.base.toErrorUiState
import com.elhady.movies.core.ui.interaction.ChipListener
import com.elhady.movies.core.ui.resource.StringsRes
import com.elhady.movies.feature.details.presentation.tvdetails.listener.TvDetailsListeners
import com.elhady.movies.feature.details.presentation.tvdetails.mapper.CastUiMapper
import com.elhady.movies.feature.details.presentation.tvdetails.mapper.TvDetailsInfoToInfoUIStateMapper
import com.elhady.movies.feature.details.presentation.tvdetails.mapper.TvDetailsReviewUiMapper
import com.elhady.movies.feature.details.presentation.tvdetails.mapper.TvDetailsSeasonUiMapper
import com.elhady.movies.feature.details.presentation.tvdetails.mapper.TvShowToUIStateMapper
import com.elhady.movies.feature.details.presentation.tvdetails.mapper.TvShowYoutubeVideoDetailsUiMapper
import com.elhady.movies.feature.details.presentation.tvdetails.mapper.UserListsUiMapper
import com.elhady.movies.feature.details.presentation.tvdetails.state.CastUIState
import com.elhady.movies.feature.details.presentation.tvdetails.state.InfoUIState
import com.elhady.movies.feature.details.presentation.tvdetails.state.RecommendationsUIState
import com.elhady.movies.feature.details.presentation.tvdetails.state.ReviewsUIState
import com.elhady.movies.feature.details.presentation.tvdetails.state.SeasonsUIState
import com.elhady.movies.feature.details.presentation.tvdetails.state.TrailerUIState
import com.elhady.movies.feature.details.presentation.tvdetails.state.TvDetailsUIState
import com.elhady.movies.feature.details.presentation.tvdetails.state.UserListsUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TvDetailsViewModel @Inject constructor(
    private val tvDetailsInfoToInfoUIStateMapper: TvDetailsInfoToInfoUIStateMapper,
    private val tvShowToUIStateMapper: TvShowToUIStateMapper,
    private val castUiMapper: CastUiMapper,
    private val tvDetailsSeasonUiMapper: TvDetailsSeasonUiMapper,
    private val tvDetailsReviewUiMapper: TvDetailsReviewUiMapper,
    private val tvDetailsInfoUseCase: GetTvDetailsInfoUseCase,
    private val getTvDetailsCastUseCase: GetTvDetailsCastUseCase,
    private val getTvDetailsSeasonsUseCase: GetTvDetailsSeasonsUseCase,
    private val rateTvShowUseCase: RateTvShowUseCase,
    private val getTvDetailsReviewsUseCase: GetTvDetailsReviewsUseCase,
    private val getTvShowRecommendationsUseCase: GetTvShowRecommendationsUseCase,
    private val getTvShowYoutubeDetailsUseCase: GetTvShowYoutubeDetailsUseCase,
    private val getUserListsUseCase: GetUserListsUseCase,
    private val addToUserListUseCase: AddToUserListUseCase,
    private val createUserListUseCase: CreateUserListUseCase,
    private val addToFavouriteUseCase: AddToFavouriteUseCase,
    private val addToWatchList: AddToWatchList,
    private val checkIsUserLoggedInUseCase: CheckIsUserLoggedInUseCase,
    private val getRatingTvUseCase: GetRatingTvUseCase,
    private val tvShowYoutubeVideoDetailsUiMapper: TvShowYoutubeVideoDetailsUiMapper,
    private val userListsUiMapper: UserListsUiMapper,
    private val stringsRes: StringsRes,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<TvDetailsUIState, TvDetailsUiEffect>(TvDetailsUIState()) {

    private val tvShowId: Int? =
        savedStateHandle.get<Int>(TV_SHOW_ID) ?: savedStateHandle.get<String>(TV_SHOW_ID)?.toIntOrNull()

    init {
        if (tvShowId != null) {
            loadTvDetails()
        } else {
            _state.update { it.copy(isLoading = false, error = listOf(stringsRes.someThingError)) }
        }
    }


    // initial loading
    private fun loadTvDetails() {
        _state.update { it.copy(isLoading = true, isLogin = checkIsUserLoggedInUseCase()) }
        getTvDetailsInfo()
        getTvDetailsCast()
        getTvDetailsSeasons()
        getTvDetailsReviews()
        getTvDetailsRecommendations()
        getYoutubeDetails()
        getTvDetailsRating()
    }


    //region info
    private fun getTvDetailsInfo() {
        tryToExecute(
            call = { tvDetailsInfoUseCase(tvShowId!!) },
            onSuccess = ::onInfoSuccess,
            onError = ::onTvDetailsInfoError

        )
    }

    private fun onInfoSuccess(data: TvDetailsInfo) {
        val infoUIState = tvDetailsInfoToInfoUIStateMapper.map(input = data)
        _state.update {
            val newState = it.copy(
                infoUIState = InfoUIState.Success(
                    info = infoUIState
                ),
                isLoading = false
            )
            newState.copy(tvDetailsItems = updateTvDetailsItems(newState))
        }
    }

    private fun onTvDetailsInfoError(error: AppException) {
        _state.update {
            val newState = it.copy(
                infoUIState = InfoUIState.Error(error = error.toErrorUiState()),
                isLoading = false
            )
            newState.copy(tvDetailsItems = updateTvDetailsItems(newState))
        }
    }
    //endregion

    //region cast
    private fun getTvDetailsCast() {
        tryToExecute(
            call = { getTvDetailsCastUseCase(tvShowId!!) },
            onSuccess = ::onCastSuccess,
            onError = ::onCastError
        )
    }

    private fun onCastSuccess(data: List<People>) {
        val cast = castUiMapper.map(data)
        _state.update {
            val newState = it.copy(
                castUIState = CastUIState.Success(cast)
            )
            newState.copy(tvDetailsItems = updateTvDetailsItems(newState))
        }
    }

    private fun onCastError(error: AppException) {
        _state.update {
            val newState = it.copy(castUIState = CastUIState.Error(error = error.toErrorUiState()))
            newState.copy(tvDetailsItems = updateTvDetailsItems(newState))
        }
    }
    //endregion

    //region seasons
    private fun getTvDetailsSeasons() {
        tryToExecute(
            call = { getTvDetailsSeasonsUseCase(tvShowId!!) },
            onSuccess = ::onSeasonsSuccess,
            onError = ::onSeasonError
        )
    }

    private fun onSeasonsSuccess(data: List<Season>) {
        val seasons = tvDetailsSeasonUiMapper.map(data)
        _state.update {
            val newState = it.copy(seasonsUIState = SeasonsUIState.Success(seasons = seasons))
            newState.copy(tvDetailsItems = updateTvDetailsItems(newState))
        }
    }

    private fun onSeasonError(error: AppException) {
        _state.update {
            val newState = it.copy(seasonsUIState = SeasonsUIState.Error(error = error.toErrorUiState()))
            newState.copy(tvDetailsItems = updateTvDetailsItems(newState))
        }
    }

    //endregion

    //region reviews
    private fun getTvDetailsReviews() {
        tryToExecute(
            call = { getTvDetailsReviewsUseCase(tvShowId!!) },
            onSuccess = ::onReviewsSuccess,
            onError = ::onReviewsError
        )
    }

    private fun onReviewsSuccess(data: List<Review>) {
        val reviews = tvDetailsReviewUiMapper.map(data)
        _state.update {
            val newState = it.copy(
                reviewsUIState = if (reviews.isEmpty()) {
                    ReviewsUIState.Empty
                } else {
                    ReviewsUIState.Success(reviews)
                }
            )
            newState.copy(tvDetailsItems = updateTvDetailsItems(newState))
        }
    }

    private fun onReviewsError(error: AppException) {
        _state.update {
            val newState = it.copy(reviewsUIState = ReviewsUIState.Error(error = error.toErrorUiState()))
            newState.copy(tvDetailsItems = updateTvDetailsItems(newState))
        }
    }

    //endregion

    //region recommendations
    private fun getTvDetailsRecommendations() {
        tryToExecute(
            call = { getTvShowRecommendationsUseCase(tvShowId!!) },
            onSuccess = ::onRecommendationsSuccess,
            onError = ::onRecommendationsError
        )
    }

    private fun onRecommendationsSuccess(data: List<TvShow>) {
        val recommendations = tvShowToUIStateMapper.map(data)
        _state.update {
            val newState = it.copy(
                recommendationsUIState = if (recommendations.isEmpty()) {
                    RecommendationsUIState.Empty
                } else {
                    RecommendationsUIState.Success(recommendations)
                }
            )
            newState.copy(tvDetailsItems = updateTvDetailsItems(newState))
        }
    }

    private fun onRecommendationsError(error: AppException) {
        _state.update {
            val newState = it.copy(recommendationsUIState = RecommendationsUIState.Error(error = error.toErrorUiState()))
            newState.copy(tvDetailsItems = updateTvDetailsItems(newState))
        }
    }
    //endregion

    // region youtube
    private fun getYoutubeDetails() {
        tryToExecute(
            call = { getTvShowYoutubeDetailsUseCase(tvShowId!!) },
            onSuccess = ::onYoutubeDetailsSuccess,
            onError = ::onYoutubeDetailsError

        )
    }

    private fun onYoutubeDetailsSuccess(data: YoutubeVideoDetails) {
        val youtubeKeyId = tvShowYoutubeVideoDetailsUiMapper.map(data)
        _state.update {
            it.copy(
                trailerUIState = if (youtubeKeyId.youtubeKey.isNotEmpty()) {
                    TrailerUIState.Available(youtubeKey = youtubeKeyId)
                } else {
                    TrailerUIState.NotAvailable
                }
            )
        }
    }

    private fun onYoutubeDetailsError(error: AppException) {
        _state.update {
            it.copy(trailerUIState = TrailerUIState.Error(error = error.toErrorUiState()))
        }
    }
    //endregion

    // region get rating
    private fun getTvDetailsRating() {
        tryToExecute(
            call = { getRatingTvUseCase(tvShowId!!) },
            onSuccess = ::onSuccessGetTvShowRating,
            onError = ::onRatingError
        )
    }

    private fun onSuccessGetTvShowRating(rate: Float) {
        _state.update {
            it.copy(
                ratingUIState = it.ratingUIState.copy(
                    rating = rate,
                    isLoading = false,
                    error = null
                )
            )
        }
    }

    private fun onRatingError(error: AppException) {
        _state.update { it.copy(ratingUIState = it.ratingUIState.copy(error = error.toErrorUiState())) }
    }

    private fun updateRating(rate: Float) {
        _state.update { it.copy(ratingUIState = it.ratingUIState.copy(rating = rate, error = null)) }
    }

    private fun submitRating() {
        tryToExecute(
            call = {
                rateTvShowUseCase(
                    rate = state.value.ratingUIState.rating.toDouble(),
                    tvShowId = tvShowId!!
                )
            },
            onSuccess = { sendEffect(TvDetailsUiEffect.ShowSnackBar(stringsRes.ratingAddSuccessFully)) },
            onError = { sendEffect(TvDetailsUiEffect.ShowSnackBar(stringsRes.someThingError)) }
        )
    }
    //endregion

    //region user lists
    private fun getUserLists() {
        tryToExecute(
            call = { getUserListsUseCase() },
            onSuccess = ::onGetUserListsSuccess,
            onError = { sendEffect(TvDetailsUiEffect.ShowSnackBar(stringsRes.someThingError)) }
        )
    }

    private fun onGetUserListsSuccess(data: List<UserList>) {
        val lists = userListsUiMapper.map(data)
        _state.update {
            it.copy(
                userListsUIState = UserListsUIState.Success(lists)
            )
        }
        sendEffect(
            TvDetailsUiEffect.ShowSaveToListBottomSheet(
                lists = lists,
                selectedLists = _state.value.userSelectedLists
            )
        )
    }

    private fun chipList(listId: Int) {
        _state.update { currentState ->

            val selectedLists =
                currentState.userSelectedLists.toMutableList()

            if (listId in selectedLists) {
                selectedLists.remove(listId)
            } else {
                selectedLists.add(listId)
            }

            currentState.copy(
                userSelectedLists = selectedLists
            )
        }

    }


    private fun addToSelectedLists() {
        state.value.userSelectedLists.forEach { listId ->

            tryToExecute(
                call = { addToUserListUseCase(listId, tvShowId!!) },
                onSuccess = {
                    sendEffect(TvDetailsUiEffect.ShowSnackBar(stringsRes.addSuccessfully))
                },
                onError = {
                    sendEffect(TvDetailsUiEffect.ShowSnackBar(stringsRes.someThingError))
                }
            )
        }
    }


    private fun createUserNewList(listName: String) {
        tryToExecute(
            call = { createUserListUseCase(listName) },
            onSuccess = {
                getUserLists()
            },
            onError = {
                sendEffect(TvDetailsUiEffect.ShowSnackBar(stringsRes.someThingError))
            }
        )
    }

    //endregion

    // region favourite / watchlist
    private fun addToFavourite() {
        tryToExecute(
            call = { addToFavouriteUseCase(tvShowId!!, "tv") },
            onSuccess = {
                sendEffect(TvDetailsUiEffect.ShowSnackBar(stringsRes.addSuccessfully))
            },
            onError = {
                sendEffect(TvDetailsUiEffect.ShowSnackBar(stringsRes.someThingError))
            }
        )
    }

    private fun addToWatchlist() {
        tryToExecute(
            call = { addToWatchList(tvShowId!!, "tv") },
            onSuccess = {
                sendEffect(TvDetailsUiEffect.ShowSnackBar(stringsRes.addSuccessfully))
            },
            onError = {
                sendEffect(TvDetailsUiEffect.ShowSnackBar(stringsRes.someThingError))
            }
        )
    }

    // endregion


    //region events

    fun onEvent(event: TvDetailsUiEvent) {
        when (event) {
            TvDetailsUiEvent.BackClicked -> {
                sendEffect(TvDetailsUiEffect.NavigateBack)
            }

            TvDetailsUiEvent.PlayClicked -> {
                handlePlayClicked()
            }

            TvDetailsUiEvent.RateClicked -> {
                handelRateClicked()
            }

            TvDetailsUiEvent.SaveClicked -> {
                handelSaveClicked()
            }

            TvDetailsUiEvent.FavouriteClicked -> {
                addToFavourite()
            }

            TvDetailsUiEvent.WatchlistClicked -> {
                addToWatchlist()
            }

            TvDetailsUiEvent.ShowMoreCastClicked -> {
                sendEffect(TvDetailsUiEffect.NavigateToShowMoreCast)
            }

            TvDetailsUiEvent.ShowMoreRecommendedClicked -> {
                sendEffect(TvDetailsUiEffect.NavigateToShowMoreRecommendation)
            }

            is TvDetailsUiEvent.PersonClicked -> {
                sendEffect(TvDetailsUiEffect.NavigateToPersonDetails(event.personId))
            }

            is TvDetailsUiEvent.SeasonClicked -> {
                sendEffect(
                    TvDetailsUiEffect.NavigateToSeasonDetails(
                        tvShowId = tvShowId!!,
                        seasonNumber = event.seasonNumber
                    )
                )
            }

            is TvDetailsUiEvent.RecommendationClicked -> {
                sendEffect(TvDetailsUiEffect.NavigateToTvDetails(event.tvShowId))
            }

            is TvDetailsUiEvent.RatingChanged -> {
                updateRating(event.rating)
            }

            TvDetailsUiEvent.RatingSubmitted -> {
                submitRating()
            }

            is TvDetailsUiEvent.ListSelected -> {
                chipList(event.listId)
            }

            TvDetailsUiEvent.DoneAddingLists -> {
                addToSelectedLists()
            }

            TvDetailsUiEvent.AddNewListClicked -> {
                // Handled via UI visibility
            }

            is TvDetailsUiEvent.CreateNewListClicked -> {
                createUserNewList(event.listName)
            }

            TvDetailsUiEvent.Retry -> {
                loadTvDetails()
            }
        }
    }


    // region event handlers
    private fun handlePlayClicked() {
        when (val trailer = state.value.trailerUIState) {
            is TrailerUIState.Available -> {
                sendEffect(TvDetailsUiEffect.NavigateToTrailer(trailer.youtubeKey.youtubeKey))
            }

            else -> {
                sendEffect(TvDetailsUiEffect.ShowSnackBar(stringsRes.noTrailer))
            }
        }
    }

    private fun handelRateClicked() {
        if (!state.value.isLogin) {
            sendEffect(TvDetailsUiEffect.ShowSnackBar(stringsRes.notLoggedInToRate))
            return
        }
        sendEffect(TvDetailsUiEffect.ShowRatingBottomSheet(rating = state.value.ratingUIState.rating))
    }

    private fun handelSaveClicked() {
        if (!state.value.isLogin) {
            sendEffect(TvDetailsUiEffect.ShowSnackBar(stringsRes.notLoggedInToRate))
            return
        }
        getUserLists()
    }

    private fun updateTvDetailsItems(state: TvDetailsUIState): List<TvDetailsItem> {
        val items = mutableListOf<TvDetailsItem>()

        when (val info = state.infoUIState) {
            is InfoUIState.Success -> {
                items.add(
                    TvDetailsItem.Info(
                        info.info
                    )
                )
            }

            else -> Unit
        }

        when (val cast = state.castUIState) {
            is CastUIState.Success -> {
                items.add(
                    TvDetailsItem.People(
                        cast.people,
                        state.seasonsUIState is SeasonsUIState.Success
                    )
                )
            }

            else -> Unit
        }

        when (val seasons = state.seasonsUIState) {
            is SeasonsUIState.Success -> {
                items.addAll(
                    seasons.seasons.map {
                        TvDetailsItem.Season(it)
                    }
                )
            }

            else -> Unit
        }

        when (val recommendations = state.recommendationsUIState) {
            is RecommendationsUIState.Success -> {
                items.add(
                    TvDetailsItem.Recommended(
                        recommendations.items,
                        state.reviewsUIState is ReviewsUIState.Success
                    )
                )
            }

            else -> Unit
        }

        when (val reviews = state.reviewsUIState) {
            is ReviewsUIState.Success -> {
                items.addAll(
                    reviews.reviews.map {
                        TvDetailsItem.Review(it)
                    }
                )
            }

            else -> Unit
        }
        return items
    }

    companion object {
        const val TV_SHOW_ID = "tvShowId"
    }
}
