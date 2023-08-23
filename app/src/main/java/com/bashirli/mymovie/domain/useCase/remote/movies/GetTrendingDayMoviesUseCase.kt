package com.bashirli.mymovie.domain.useCase.remote.movies

import com.bashirli.mymovie.domain.repository.remote.ApiRepository
import javax.inject.Inject

class GetTrendingDayMoviesUseCase @Inject constructor(
    private val repo: ApiRepository
) {

    suspend operator fun invoke()=repo.getTrendingDayMovies()

}