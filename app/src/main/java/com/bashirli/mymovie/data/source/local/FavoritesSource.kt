package com.bashirli.mymovie.data.source.local

import com.bashirli.mymovie.common.util.Resource
import com.bashirli.mymovie.data.dto.local.FavoritesDTO

interface FavoritesSource {

    suspend fun insertItem(favoritesDTO: FavoritesDTO)

    suspend fun deleteItem(id:Int)

    suspend fun getAllFavorites():Resource<List<FavoritesDTO>>

    suspend fun checkItemHaveInTable(id:Int) : Resource<Boolean>

}