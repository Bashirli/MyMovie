package com.bashirli.mymovie.domain.useCase.celeb

import com.bashirli.mymovie.domain.repository.ApiRepository
import javax.inject.Inject

class GetCelebMoviesUseCase @Inject constructor(
    private val repo:ApiRepository
) {

    suspend operator fun invoke(personId:Int) = repo.getCelebMovies(personId)

}