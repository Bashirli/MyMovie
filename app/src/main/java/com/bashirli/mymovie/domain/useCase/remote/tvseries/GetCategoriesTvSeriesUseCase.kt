package com.bashirli.mymovie.domain.useCase.remote.tvseries

import com.bashirli.mymovie.domain.repository.remote.ApiRepository
import javax.inject.Inject

class GetCategoriesTvSeriesUseCase @Inject constructor(private val repo: ApiRepository) {

    suspend operator fun invoke()=repo.getTvSeriesCategories()

}