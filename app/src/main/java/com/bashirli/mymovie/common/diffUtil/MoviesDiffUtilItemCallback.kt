package com.bashirli.mymovie.common.diffUtil

import androidx.recyclerview.widget.DiffUtil
import com.bashirli.mymovie.domain.model.movies.MoviesModel
import com.bashirli.mymovie.domain.model.movies.ResultModel

object MoviesDiffUtilItemCallback:DiffUtil.ItemCallback<ResultModel>(){
    override fun areItemsTheSame(oldItem: ResultModel, newItem: ResultModel): Boolean {
        return oldItem.id==newItem.id
    }

    override fun areContentsTheSame(oldItem: ResultModel, newItem: ResultModel): Boolean {
        return oldItem==newItem
    }



}