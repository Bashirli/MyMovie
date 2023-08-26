package com.bashirli.mymovie.domain.model.categorySelected

import com.bashirli.mymovie.data.dto.selectedCategory.Item
import com.google.gson.annotations.SerializedName

data class CategoryDetailsModel(
    val createdBy: String?,
    val description: String?,
    val favoriteCount: Int?,
    val id: String?,
    val itemCount: Int?,
    val items: List<ItemModel>?,
    val name: String?,
    val posterPath: String?
)