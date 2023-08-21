package com.bashirli.mymovie.domain.model.details.reviews

import android.os.Parcelable
import com.bashirli.mymovie.data.dto.details.reviews.AuthorDetails
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResultModel (
    val author: String,
    val authorDetails: AuthorModel,
    val content: String,
    val createdAt: String,
    val id: String,
    val updatedAt: String,
    val url: String
    ) : Parcelable