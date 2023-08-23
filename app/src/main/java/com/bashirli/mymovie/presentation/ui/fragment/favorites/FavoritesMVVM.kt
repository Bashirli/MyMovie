package com.bashirli.mymovie.presentation.ui.fragment.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bashirli.mymovie.common.util.Status
import com.bashirli.mymovie.data.dto.local.FavoritesDTO
import com.bashirli.mymovie.domain.model.local.FavoritesModel
import com.bashirli.mymovie.domain.useCase.local.FavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesMVVM @Inject constructor(
    private val favoritesUseCase : FavoritesUseCase
) : ViewModel() {

    private val _liveData = MutableLiveData<FavoritesState>()
    val liveData : LiveData<FavoritesState> get()=_liveData

    init {
        getAllFavorites()
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

    private fun getAllFavorites(){
        viewModelScope.launch {
            favoritesUseCase.getAllFavorites().collectLatest {
                when(it.status){
                    Status.SUCCESS -> {
                        it.data?.let {
                            _liveData.value=FavoritesState.Favorites(it)
                        }
                    }
                    Status.ERROR -> {
                        _liveData.value=FavoritesState.Error(it.message?:"Error")
                    }
                    Status.LOADING -> _liveData.value=FavoritesState.Loading
                }
            }
        }
    }


    sealed class FavoritesState(){

        object Loading : FavoritesState()

        data class Error(val message:String) : FavoritesState()

        data class Favorites(val data : List<FavoritesModel>) : FavoritesState()

    }


}