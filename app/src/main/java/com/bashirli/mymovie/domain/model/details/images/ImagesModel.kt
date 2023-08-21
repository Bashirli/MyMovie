package com.bashirli.mymovie.domain.model.details.images

import android.os.Parcelable
import com.bashirli.mymovie.data.dto.details.images.Backdrop
import com.bashirli.mymovie.data.dto.details.images.Logo
import com.bashirli.mymovie.data.dto.details.images.Poster
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImagesModel(
    val backdrops: List<BackdropModel>,
    val id: Int,
) : Parcelable
