package com.bashirli.mymovie.domain.useCase.local

import com.bashirli.mymovie.data.dto.local.FavoritesDTO
import com.bashirli.mymovie.domain.repository.local.FavoritesRepository
import javax.inject.Inject

class FavoritesUseCase @Inject constructor(
    private val repo:FavoritesRepository
) {

    suspend fun getAllFavorites()=repo.getAllFavorites()

    suspend fun insertItem(item:FavoritesDTO) = repo.insertItem(item)

    suspend fun deleteItem(id:Int) = repo.deleteItem(id)

    suspend fun checkItemHaveInTable(id:Int) = repo.checkItemHaveInTable(id)

}