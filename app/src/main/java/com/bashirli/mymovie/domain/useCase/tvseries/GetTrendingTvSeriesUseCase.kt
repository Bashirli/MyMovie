package com.bashirli.mymovie.domain.useCase.tvseries

import com.bashirli.mymovie.domain.repository.ApiRepository
import javax.inject.Inject

class GetTrendingTvSeriesUseCase @Inject constructor(private val repo:ApiRepository) {

    suspend operator fun invoke()=repo.getTvSeriesTrending()

}