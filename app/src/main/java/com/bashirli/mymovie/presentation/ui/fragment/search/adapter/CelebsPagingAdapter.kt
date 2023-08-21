package com.bashirli.mymovie.presentation.ui.fragment.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bashirli.mymovie.common.diffUtil.CelebsDiffUtilItemCallback
import com.bashirli.mymovie.databinding.ItemCelebsBinding
import com.bashirli.mymovie.domain.model.celebs.CelebsResultModel

class CelebsPagingAdapter : PagingDataAdapter<CelebsResultModel,CelebsPagingAdapter.CelebsViewHolder>(
    CelebsDiffUtilItemCallback
) {

    var onClickCelebsItem:(CelebsResultModel)->Unit={}

    inner class CelebsViewHolder(private val binding:ItemCelebsBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item:CelebsResultModel){
            binding.celebsResultModel=item
            binding.executePendingBindings()
        }
        fun find(item:CelebsResultModel,onClickCelebsItem:(CelebsResultModel)->Unit={}){
            binding.mainRoot.setOnClickListener{
                onClickCelebsItem(item)
            }
        }
    }

    override fun onBindViewHolder(holder: CelebsViewHolder, position: Int) {
        val item=getItem(position)
        item?.let {
            holder.bind(item)
            holder.find(item,onClickCelebsItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CelebsViewHolder {
        val layout=ItemCelebsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CelebsViewHolder(layout)
    }

}