package com.bashirli.mymovie.domain.repository.local

import com.bashirli.mymovie.common.util.Resource
import com.bashirli.mymovie.data.dto.local.FavoritesDTO
import com.bashirli.mymovie.domain.model.local.FavoritesModel
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository  {

    suspend fun insertItem(favoritesDTO: FavoritesDTO)

    suspend fun deleteItem(id: Int)

    suspend fun getAllFavorites(): Flow<Resource<List<FavoritesModel>>>

    suspend fun checkItemHaveInTable(id:Int) : Flow<Resource<Boolean>>

}