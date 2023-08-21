package com.bashirli.mymovie.domain.useCase.celeb

import com.bashirli.mymovie.domain.repository.ApiRepository
import javax.inject.Inject

class GetCelebTvSeriesUseCase @Inject constructor(
    private val repo : ApiRepository
) {

    suspend operator fun invoke(personId : Int) = repo.getCelebTvSeries(personId)

}