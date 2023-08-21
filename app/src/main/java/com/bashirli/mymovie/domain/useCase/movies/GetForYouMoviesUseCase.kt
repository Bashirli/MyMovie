package com.bashirli.mymovie.domain.useCase.movies

import com.bashirli.mymovie.domain.repository.ApiRepository
import javax.inject.Inject

class GetForYouMoviesUseCase @Inject constructor(private val repo:ApiRepository) {

    suspend operator fun invoke()=repo.getForYouMovies()

}