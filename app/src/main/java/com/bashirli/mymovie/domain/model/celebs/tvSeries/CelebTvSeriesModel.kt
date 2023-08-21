package com.bashirli.mymovie.domain.model.celebs.tvSeries

import com.bashirli.mymovie.data.dto.celebs.tvseries.CelebTvCast
import com.bashirli.mymovie.data.dto.celebs.tvseries.CelebTvCrew
import com.google.gson.annotations.SerializedName

data class CelebTvSeriesModel (
    val celebTvCast: List<CelebTvCastModel>,
    val celebTvCrew: List<CelebTvCrewModel>,
    val id: Int?
)