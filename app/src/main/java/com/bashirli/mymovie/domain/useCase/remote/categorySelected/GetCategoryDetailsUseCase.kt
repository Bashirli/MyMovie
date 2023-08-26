package com.bashirli.mymovie.domain.useCase.remote.categorySelected

import com.bashirli.mymovie.domain.repository.remote.ApiRepository
import javax.inject.Inject

class GetCategoryDetailsUseCase @Inject constructor(
    private val repo:ApiRepository
) {

    suspend operator fun invoke(listId:Int) = repo.getCategoryDetails(listId)

}