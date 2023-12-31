package com.bashirli.mymovie.domain.useCase.remote.movies

import com.bashirli.mymovie.domain.repository.remote.ApiRepository
import javax.inject.Inject

class GetRecommendationsUseCase @Inject constructor(
    private val repo: ApiRepository
) {

    suspend operator fun invoke(movie_id:Int)=repo.getRecommendations(movie_id)

}