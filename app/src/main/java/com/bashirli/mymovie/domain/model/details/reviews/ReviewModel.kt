package com.bashirli.mymovie.domain.model.details.reviews

import android.os.Parcelable
import com.bashirli.mymovie.data.dto.details.reviews.Result
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ReviewModel(
    val id: Int,
    val page: Int,
    val results: List<ResultModel>,
    val totalPages: Int,
    val totalResults: Int
) : Parcelable
