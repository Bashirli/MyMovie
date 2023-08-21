package com.bashirli.mymovie.common.diffUtil

import androidx.recyclerview.widget.DiffUtil
import com.bashirli.mymovie.domain.model.tvseries.TvSeriesResultModel

object TvSeriesDiffUtil : DiffUtil.ItemCallback<TvSeriesResultModel>() {
    override fun areItemsTheSame(
        oldItem: TvSeriesResultModel,
        newItem: TvSeriesResultModel,
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: TvSeriesResultModel,
        newItem: TvSeriesResultModel,
    ): Boolean {
        return oldItem==newItem
    }


}