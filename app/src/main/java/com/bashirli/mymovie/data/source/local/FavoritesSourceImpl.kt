package com.bashirli.mymovie.data.source.local

import com.bashirli.mymovie.common.util.Resource
import com.bashirli.mymovie.data.dto.local.FavoritesDTO
import com.bashirli.mymovie.data.service.local.RoomDAO
import javax.inject.Inject

class FavoritesSourceImpl @Inject constructor(
    private val service : RoomDAO
) : FavoritesSource {
    override suspend fun insertItem(favoritesDTO: FavoritesDTO) {
        service.insertItem(favoritesDTO)
    }

    override suspend fun deleteItem(id: Int) {
        service.deleteItem(id)
    }

    override suspend fun getAllFavorites(): Resource<List<FavoritesDTO>> {
        return try {
            val response = service.getAllFavorites()
            Resource.success(response)
        }catch (e:Exception){
            Resource.error(e.localizedMessage,null)
        }
    }

    override suspend fun checkItemHaveInTable(id: Int): Resource<Boolean> {
        return try {
            val response = service.checkItemHaveInTable(id)
            Resource.success(response)
        }catch (e:Exception){
            Resource.error(e.localizedMessage,null)
        }
    }
}