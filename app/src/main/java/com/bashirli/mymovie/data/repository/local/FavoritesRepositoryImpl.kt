package com.bashirli.mymovie.data.repository.local

import com.bashirli.mymovie.common.util.Resource
import com.bashirli.mymovie.common.util.Status
import com.bashirli.mymovie.data.dto.local.FavoritesDTO
import com.bashirli.mymovie.data.mapper.toFavoritesModel
import com.bashirli.mymovie.data.source.local.FavoritesSource
import com.bashirli.mymovie.domain.model.local.FavoritesModel
import com.bashirli.mymovie.domain.repository.local.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    private val source:FavoritesSource
) : FavoritesRepository {
    override suspend fun insertItem(favoritesDTO: FavoritesDTO) {
        source.insertItem(favoritesDTO)
    }

    override suspend fun deleteItem(id: Int) {
        source.deleteItem(id)
    }

    override suspend fun getAllFavorites(): Flow<Resource<List<FavoritesModel>>> = flow {
        emit(Resource.loading(null))
        val response = source.getAllFavorites()
        when(response.status){
            Status.SUCCESS -> {
                emit(Resource.success(response.data?.toFavoritesModel()))
            }
            Status.ERROR -> {
                emit(Resource.error(response.message?:"Error",null))
            }
            else ->{Resource.loading(null)}
        }
    }

    override suspend fun checkItemHaveInTable(id: Int): Flow<Resource<Boolean>> = flow {
        emit(Resource.loading(null))
        val response = source.checkItemHaveInTable(id)

        when(response.status){
            Status.SUCCESS -> {
                emit(Resource.success(response.data))
            }
            Status.ERROR -> {
                emit(Resource.error(response.message?:"Error",null))
            }
            else ->{Resource.loading(null)}
        }


    }
}