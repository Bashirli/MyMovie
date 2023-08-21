package com.bashirli.mymovie.common.diffUtil

import androidx.recyclerview.widget.DiffUtil
import com.bashirli.mymovie.domain.model.celebs.CelebsResultModel

object CelebsDiffUtilItemCallback : DiffUtil.ItemCallback<CelebsResultModel>() {
    override fun areItemsTheSame(oldItem: CelebsResultModel, newItem: CelebsResultModel): Boolean {
        return oldItem.id==newItem.id
    }

    override fun areContentsTheSame(
        oldItem: CelebsResultModel,
        newItem: CelebsResultModel,
    ): Boolean {
        return oldItem==newItem
    }

}