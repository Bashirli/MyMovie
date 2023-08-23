package com.bashirli.mymovie.domain.useCase.remote.details

import com.bashirli.mymovie.domain.repository.remote.ApiRepository
import javax.inject.Inject

class GetMovieCategoriesUseCase @Inject constructor(private val repo: ApiRepository) {

    suspend operator fun invoke()=repo.getMovieCategories()

}