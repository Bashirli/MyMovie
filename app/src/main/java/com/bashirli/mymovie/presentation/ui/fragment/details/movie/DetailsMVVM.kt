package com.bashirli.mymovie.presentation.ui.fragment.details.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bashirli.mymovie.common.util.Resource
import com.bashirli.mymovie.common.util.Status
import com.bashirli.mymovie.data.dto.local.FavoritesDTO
import com.bashirli.mymovie.domain.model.details.movie.DetailsModel
import com.bashirli.mymovie.domain.model.details.cast.CastBaseModel
import com.bashirli.mymovie.domain.model.details.images.ImagesModel
import com.bashirli.mymovie.domain.model.details.reviews.ReviewModel
import com.bashirli.mymovie.domain.model.movies.ResultModel
import com.bashirli.mymovie.domain.useCase.local.FavoritesUseCase
import com.bashirli.mymovie.domain.useCase.remote.details.GetDetailsCreditUseCase
import com.bashirli.mymovie.domain.useCase.remote.details.GetDetailsImagesUseCase
import com.bashirli.mymovie.domain.useCase.remote.details.GetMovieDetailsUseCase
import com.bashirli.mymovie.domain.useCase.remote.details.GetMovieReviewsUseCase
import com.bashirli.mymovie.domain.useCase.remote.movies.GetRecommendationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsMVVM @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getDetailsCreditUseCase: GetDetailsCreditUseCase,
    private val getDetailsImagesUseCase: GetDetailsImagesUseCase,
    private val getRecommendationsUseCase: GetRecommendationsUseCase,
    private val getMovieReviewsUseCase: GetMovieReviewsUseCase,
    private val favoritesUseCase: FavoritesUseCase
) : ViewModel() {

    private val _liveData=MutableLiveData<State>()
    val liveData: LiveData<State> get()=_liveData



    private val _recommendationDetails=MutableLiveData<PagingData<ResultModel>>(PagingData.empty())
    val recommendationDetails: LiveData<PagingData<ResultModel>> get()=_recommendationDetails

    fun getData(movie_id:Int){
        viewModelScope.launch {
            getMovieDetailsUseCase(movie_id).collectLatest {
                _liveData.value =  when(it.status){
                    Status.SUCCESS->{
                        it.data?.let {
                             State.MovieDetails(it)
                        }
                    }
                    Status.ERROR->{
                        State.Error(it.message?:"Error")
                    }
                    Status.LOADING->{
                        State.Loading
                    }
                }
            }
        }
    }

    fun getCredits(movie_id:Int){
        viewModelScope.launch {
            getDetailsCreditUseCase(movie_id).collectLatest {
                when(it.status){
                    Status.SUCCESS->{
                        it.data?.let {
                            _liveData.value =   State.Credits(it)
                        }
                    }
                    Status.ERROR->{
                        _liveData.value =   State.Error(it.message?:"Error")
                    }
                    Status.LOADING->{

                    }
                }
            }
        }
    }

    fun getImages(movie_id:Int){
        viewModelScope.launch {
            getDetailsImagesUseCase(movie_id).collectLatest {
                when(it.status){
                    Status.SUCCESS->{
                        it.data?.let {
                            _liveData.value = State.Images(it)
                        }
                    }
                    Status.ERROR->{
                        _liveData.value = State.Error(it.message?:"Error")
                    }
                    Status.LOADING->{

                    }
                }
            }
        }
    }

    fun getRecommendations(movie_id:Int){
        viewModelScope.launch {
            getRecommendationsUseCase(movie_id).cachedIn(viewModelScope).collectLatest {
                _recommendationDetails.postValue(it)
            }
        }
    }

    fun getReviews(movie_id:Int){
        viewModelScope.launch {
            getMovieReviewsUseCase(movie_id).collectLatest {
                when(it.status){
                    Status.SUCCESS->{
                        it.data?.let {
                            _liveData.value = State.Reviews(it)
                        }
                    }
                    Status.ERROR->{
                        _liveData.value = State.Error(it.message?:"Error")
                    }
                    Status.LOADING->{

                    }
                }
            }
        }
    }

    fun checkItemHaveInTable(id:Int){
        viewModelScope.launch {
            favoritesUseCase.checkItemHaveInTable(id).collectLatest {
                when(it.status){
                    Status.SUCCESS -> {
                        it.data?.let {
                            _liveData.value=State.IsInFav(it)
                        }
                    }
                    Status.ERROR -> {
                        _liveData.value=State.Error(it.message?:"Error")
                    }
                    Status.LOADING -> _liveData.value=State.Loading
                }
            }
        }
    }


    fun deleteItem(id:Int){
        viewModelScope.launch {
            favoritesUseCase.deleteItem(id)
        }
    }

    fun insertItem(favoritesDTO: FavoritesDTO){
        viewModelScope.launch {
            favoritesUseCase.insertItem(favoritesDTO)
        }
    }

    sealed class State(){
        data class MovieDetails(val data : DetailsModel) : State()

        data class Credits(val data : CastBaseModel) : State()

        data class Images(val data : ImagesModel) : State()

        data class Reviews(val data : ReviewModel) : State()

        data class IsInFav(val state:Boolean) : State()

        object Loading : State()

        data class Error(val message:String) : State()

    }


}