package com.bashirli.mymovie.domain.useCase.movies

import com.bashirli.mymovie.domain.repository.ApiRepository
import javax.inject.Inject

class GetTrendingDayMoviesUseCase @Inject constructor(
    private val repo:ApiRepository
) {

    suspend operator fun invoke()=repo.getTrendingDayMovies()

}