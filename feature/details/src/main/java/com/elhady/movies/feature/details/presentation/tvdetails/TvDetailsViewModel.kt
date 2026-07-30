package com.elhady.movies.feature.details.presentation.tvdetails

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import com.elhady.movies.core.domain.model.people.People
import com.elhady.movies.core.domain.model.common.Review
import com.elhady.movies.core.domain.model.tvshow.Season
import com.elhady.movies.core.domain.model.common.Status
import com.elhady.movies.core.domain.model.tvshow.TvShow
import com.elhady.movies.core.domain.model.account.UserList
import com.elhady.movies.core.domain.model.common.YoutubeVideoDetails
import com.elhady.movies.core.domain.model.tvshow.TvDetailsInfo
import com.elhady.movies.core.domain.usecase.auth.CheckIsUserLoggedInUseCase
import com.elhady.movies.core.domain.usecase.account.AddToFavouriteUseCase
import com.elhady.movies.core.domain.usecase.account.AddToWatchList
import com.elhady.movies.core.domain.usecase.account.AddToUserListUseCase
import com.elhady.movies.core.domain.usecase.account.CreateUserListUseCase
import com.elhady.movies.core.domain.usecase.tvshow.GetRatingTvUseCase
import com.elhady.movies.core.domain.usecase.tvshow.GetTVDetailsInfoUseCase
import com.elhady.movies.core.domain.usecase.tvshow.GetTvDetailsCreditUseCase
import com.elhady.movies.core.domain.usecase.tvshow.GetTvDetailsReviewsUseCase
import com.elhady.movies.core.domain.usecase.tvshow.GetTvDetailsSeasonsUseCase
import com.elhady.movies.core.domain.usecase.tvshow.GetTvShowRecommendationsUseCase
import com.elhady.movies.core.domain.usecase.tvshow.GetTvShowYoutubeDetailsUseCase
import com.elhady.movies.core.domain.usecase.account.GetUserListsUseCase
import com.elhady.movies.core.domain.usecase.tvshow.RateTvShowUseCase
import com.elhady.movies.core.ui.base.BaseViewModel
import com.elhady.movies.core.ui.resource.StringsRes
import com.elhady.movies.feature.details.presentation.tvdetails.listener.TvDetailsListeners
import com.elhady.movies.feature.details.presentation.tvdetails.mapper.PeopleUiMapper
import com.elhady.movies.feature.details.presentation.tvdetails.mapper.TvDetailsInfoUiMapper
import com.elhady.movies.feature.details.presentation.tvdetails.mapper.TvDetailsReviewUiMapper
import com.elhady.movies.feature.details.presentation.tvdetails.mapper.TvDetailsSeasonUiMapper
import com.elhady.movies.feature.details.presentation.tvdetails.mapper.TvShowUiMapper
import com.elhady.movies.feature.details.presentation.tvdetails.mapper.TvShowYoutubeVideoDetailsUiMapper
import com.elhady.movies.feature.details.presentation.tvdetails.mapper.UserListsUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TvDetailsViewModel @Inject constructor(
    private val tvDetailsInfoUiMapper: TvDetailsInfoUiMapper,
    private val tvShowUiMapper: TvShowUiMapper,
    private val peopleUiMapper: PeopleUiMapper,
    private val tvDetailsInfoUseCase: GetTVDetailsInfoUseCase,
    private val getTvDetailsCreditUseCase: GetTvDetailsCreditUseCase,
    private val getTvDetailsSeasonsUseCase: GetTvDetailsSeasonsUseCase,
    private val tvShowUseCase: RateTvShowUseCase,
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
) : BaseViewModel<TvDetailsUiState, TvDetailsUiEvent>(TvDetailsUiState()), TvDetailsListeners {

    private val tvShowId =
        savedStateHandle.get<Int>("tvShowId") ?: 44217

    init {
        getData()
    }

    private fun getData() {
        _state.update { it.copy(isLogin = checkIsUserLoggedInUseCase()) }
        getTvRecommendations()
        getYoutubeDetails()
        getTvShowInfo()
        getTvShowCast()
        getTvSeasons()
        getTvReviews()
        getRatingTv()
    }

    fun emptyUserLists() {
        _state.update {
            it.copy(userLists = emptyList())
        }
    }

    //region info
    private fun getTvShowInfo() {
        updateLoading(true)
        tryToExecute(
            call = { tvDetailsInfoUseCase(tvShowId) },
            onSuccess = ::onSuccessTvShowInfo,
            onError = ::onError
        )
    }

    private fun onSuccessTvShowInfo(tvShowInfoEntity: TvDetailsInfo) {
        updateLoading(false)
        val item = tvDetailsInfoUiMapper.map(tvShowInfoEntity)
        _state.update {
            it.copy(
                info = it.info.copy(
                    backdropImageUrl = item.info.backdropImageUrl,
                    name = item.info.name,
                    rating = item.info.rating,
                    description = item.info.description,
                    genres = item.info.genres,
                    isLogin = checkIsUserLoggedInUseCase()
                )
            )
        }
    }
    //endregion

    //region youtube
    private fun getYoutubeDetails() {
        updateLoading(true)
        tryToExecute(
            call = { getTvShowYoutubeDetailsUseCase(tvShowId) },
            onSuccess = ::onYoutubeDetailsSuccess,
            onError = {}
        )
    }

    private fun onYoutubeDetailsSuccess(youtubeVideoEntity: YoutubeVideoDetails) {
        val item = tvShowYoutubeVideoDetailsUiMapper.map(youtubeVideoEntity)
        _state.update {
            it.copy(
                youtubeKeyId = item.youtubeKeyId
            )
        }
    }
    //endregion

    //region cast
    private fun getTvShowCast() {
        updateLoading(true)
        tryToExecute(
            call = { getTvDetailsCreditUseCase(tvShowId) },
            onSuccess = ::onTvDetailsCastSuccess,
            onError = ::onError
        )
    }

    private fun onTvDetailsCastSuccess(castEntity: List<People>) {
        updateLoading(false)
        val item = peopleUiMapper.map(castEntity)
        _state.update {
            it.copy(
                cast = item
            )
        }
    }
    //endregion

    fun addToFavourite() {
        tryToExecute(
            call = { addToFavouriteUseCase(tvShowId, "tv") },
            onSuccess = {
                sendEvent(TvDetailsUiEvent.OnFavourite(stringsRes.addSuccessfully))
            },
            onError = {
                sendEvent(TvDetailsUiEvent.OnFavourite(stringsRes.someThingError))
            }
        )
        Log.d("FAV TV", "$tvShowId")
    }

    fun addToWatchlist() {
        tryToExecute(
            call = { addToWatchList(tvShowId, "tv") },
            onSuccess = {
                sendEvent(TvDetailsUiEvent.OnWatchList(stringsRes.addSuccessfully))
            },
            onError = {
                sendEvent(TvDetailsUiEvent.OnWatchList(stringsRes.someThingError))
            }
        )
        Log.d("WAT TV", "$tvShowId")

    }

    //region seasons
    private fun getTvSeasons() {
        updateLoading(true)
        tryToExecute(
            call = { getTvDetailsSeasonsUseCase(tvShowId) },
            onSuccess = ::onTvDetailsSeasonSuccess,
            onError = ::onError
        )
    }

    private fun onTvDetailsSeasonSuccess(seasons: List<Season>) {
        updateLoading(false)
        val item = TvDetailsSeasonUiMapper().map(seasons)
        _state.update { it.copy(seasons = item.seasons) }
    }

    //endregion

    //region recommendations
    private fun getTvRecommendations() {
        updateLoading(true)
        tryToExecute(
            call = { getTvShowRecommendationsUseCase(tvShowId) },
            onSuccess = ::onTvShowRecommendationsSuccess,
            onError = ::onError
        )
    }

    private fun onTvShowRecommendationsSuccess(recommendations: List<TvShow>) {
        updateLoading(false)
        val item = tvShowUiMapper.map(recommendations)
        _state.update {
            it.copy(
                recommended = item.recommended
            )
        }
    }
    //endregion

    //region rating
    fun updateRatingUiState(rate: Float) {
        _state.update {
            it.copy(
                userRating = rate
            )
        }
    }

    fun onRatingSubmit() {
        tryToExecute(
            call = { tvShowUseCase(state.value.userRating.toDouble(), tvShowId) },
            onSuccess = ::onRatingSuccess,
            onError = {
                sendEvent(TvDetailsUiEvent.ApplyRating(stringsRes.someThingErrorWhenAddRating))
            }
        )
    }

    private fun onRatingSuccess(statusEntity: Status) {
        sendEvent(TvDetailsUiEvent.ApplyRating(stringsRes.ratingAddSuccessFully))

        Log.d("RateTVSuccess", "${state.value.userRating}  TV Show Id $tvShowId")
    }

    private fun getRatingTv() {
        tryToExecute(
            call = { getRatingTvUseCase(tvShowId) },
            onSuccess = ::onSuccessGetRating,
            onError = ::onError
        )
        Log.d("GETRATETV", "${state.value.userRating}  TV Show Id $tvShowId")
    }

    private fun onSuccessGetRating(rate: Float){
        val rate =
        _state.update { it.copy(userRating = rate) }
        Log.d("OnSuccessRATE", "${state.value.userRating }")

    }
    //endregion

    //region reviews
    private fun getTvReviews() {
        updateLoading(true)
        tryToExecute(
            call = { getTvDetailsReviewsUseCase(tvShowId) },
            onSuccess = ::onTvDetailsReviewsSuccess,
            onError = ::onError
        )
    }

    private fun onTvDetailsReviewsSuccess(seasons: List<Review>) {
        updateLoading(false)
        val item = TvDetailsReviewUiMapper().map(seasons)
        _state.update {
            it.copy(
                reviews = item
            )
        }
    }
    //endregion

    //region user lists
    fun getUserLists() {
        tryToExecute(
            call = { getUserListsUseCase() },
            onSuccess = ::onGetUserListsUseCase,
            onError = {
                sendEvent(TvDetailsUiEvent.ShowSnackBar(stringsRes.someThingError))
            }
        )
    }

    private fun onGetUserListsUseCase(userListsEntity: List<UserList>) {
        val item = userListsUiMapper.map(userListsEntity)
        _state.update {
            it.copy(
                userLists = item.userLists
            )
        }
    }

    fun onDone(listsId: List<Int>) {
        listsId.forEach { id ->
            tryToExecute(
                call = { addToUserListUseCase(id, tvShowId) },
                onSuccess = ::onDoneSuccess,
                onError = {
                    Log.i("chip", stringsRes.someThingError + "${it.message}")
                }
            )
        }

    }

    private fun onDoneSuccess(statusEntity: Status) {
        sendEvent(TvDetailsUiEvent.OnDoneAdding("adding was successful"))
    }

    fun createUserNewList(listName: String) {
        tryToExecute(
            call = { createUserListUseCase(listName) },
            onSuccess = ::onCreateUserNewList,
            onError = {
                sendEvent(TvDetailsUiEvent.OnCreateNewList(stringsRes.someThingError))
            }
        )
    }

    private fun onCreateUserNewList(statusEntity: Status) {
        sendEvent(TvDetailsUiEvent.OnCreateNewList(stringsRes.newListAddSuccessFully))
        getUserLists()
    }
    //endregion

    //region events
    override fun onRateButtonClick() {
        if (state.value.isLogin) {
            sendEvent(TvDetailsUiEvent.RateTvEvent)
        } else {
            sendEvent(TvDetailsUiEvent.ShowSnackBar(stringsRes.notLoggedInToRate))
        }
    }

    override fun onClickPeople(id: Int) {
        sendEvent(TvDetailsUiEvent.OnPersonClick(id))
    }

    override fun onClickMedia(id: Int) {
        sendEvent(TvDetailsUiEvent.OnRecommended(id))
    }

    override fun onClickSeason(seasonNumber: Int) {
        sendEvent(TvDetailsUiEvent.OnSeasonClick(seasonNumber))
    }

    override fun onShowMoreCast() {
        sendEvent(TvDetailsUiEvent.OnShowMoreCast)
    }

    override fun onShowMoreRecommended() {
        sendEvent(TvDetailsUiEvent.OnShowMoreRecommended)
    }

    override fun onClickPlayButton() {
        sendEvent(TvDetailsUiEvent.PlayButton(state.value.youtubeKeyId))
    }

    override fun onChipClick(id: Int) {
        val updatedList = state.value.userSelectedLists.toMutableList()
        if (updatedList.remove(id)) Unit else updatedList.add(id)

        _state.update {
            it.copy(
                userSelectedLists = updatedList
            )
        }
    }


    fun onBack() {
        sendEvent(TvDetailsUiEvent.Back)
    }

    fun onClickSaveButton() {
        sendEvent(TvDetailsUiEvent.OnSaveButtonClick(tvShowId))
    }
    //endregion

    //region util
    private fun onError(th: Throwable) {
        val errors = _state.value.onErrors.toMutableList()
        errors.add(th.message.toString())
        _state.update { it.copy(onErrors = errors, isLoading = false) }
    }

    private fun updateLoading(value: Boolean) {
        _state.update { it.copy(isLoading = value) }
    }

    fun refreshScreen() {
        getData()
        _state.update { it.copy(onErrors = emptyList()) }
    }
    //endregion

    fun onApplyRateBottomSheet() {
        onRatingSubmit()
    }

    fun updateRatingValue(rate: Float) {
       updateRatingUiState(rate)
    }

    fun getUserRating(): Float {
        return state.value.userRating.div(2)
    }
}
