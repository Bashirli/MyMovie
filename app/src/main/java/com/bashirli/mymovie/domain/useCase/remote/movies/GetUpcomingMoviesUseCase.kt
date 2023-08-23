package com.bashirli.mymovie.domain.useCase.remote.movies

import com.bashirli.mymovie.domain.repository.remote.ApiRepository
import javax.inject.Inject

class GetUpcomingMoviesUseCase @Inject constructor(
    private val repo: ApiRepository
) {

    suspend operator fun invoke()=repo.getUpcomingMovies()

}