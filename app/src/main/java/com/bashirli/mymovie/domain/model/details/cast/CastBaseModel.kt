package com.bashirli.mymovie.domain.model.details.cast

import com.bashirli.mymovie.data.dto.details.cast.Cast
import com.bashirli.mymovie.data.dto.details.cast.Crew
import com.google.gson.annotations.SerializedName

data class CastBaseModel(
    val cast: List<CastModel>,
    val crew: List<CrewModel>,
    val id: Int
)