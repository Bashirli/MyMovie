package com.bashirli.mymovie.data.dto.category


import com.google.gson.annotations.SerializedName

data class GenreDTO(
    @SerializedName("genres")
    val genres: List<Genre>
)