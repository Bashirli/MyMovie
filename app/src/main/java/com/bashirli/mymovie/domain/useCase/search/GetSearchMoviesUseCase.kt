package com.bashirli.mymovie.domain.useCase.search

import com.bashirli.mymovie.domain.repository.ApiRepository
import javax.inject.Inject

class GetSearchMoviesUseCase @Inject constructor(
    private val repo:ApiRepository
) {

    suspend operator fun invoke(query:String) = repo.getSearchMovies(query)

}