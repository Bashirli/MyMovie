package com.bashirli.mymovie.presentation.ui.fragment.home.tvseries.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bashirli.mymovie.common.diffUtil.TvSeriesDiffUtil
import com.bashirli.mymovie.databinding.ItemTvSeriesBinding
import com.bashirli.mymovie.domain.model.tvseries.TvSeriesResultModel

class TvSeriesPagingAdapter : PagingDataAdapter<TvSeriesResultModel,TvSeriesPagingAdapter.TvSeriesViewHolder>
    (TvSeriesDiffUtil) {


    var onClickSeriesItem : (TvSeriesResultModel) -> Unit = {}

    inner class TvSeriesViewHolder (private val binding : ItemTvSeriesBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item:TvSeriesResultModel){
            binding.tvSeriesData=item
            binding.executePendingBindings()
        }
        fun find(item: TvSeriesResultModel,onClickSeriesItem : (TvSeriesResultModel) -> Unit = {}){
            binding.cardView.setOnClickListener {
                onClickSeriesItem(item)
            }
        }
    }

    override fun onBindViewHolder(holder: TvSeriesViewHolder, position: Int) {
        getItem(position)?.let {
            val item = it
            holder.bind(item)
            holder.find(item, onClickSeriesItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvSeriesViewHolder {
        val layout = ItemTvSeriesBinding.inflate(LayoutInflater.from(parent.context) , parent , false )
        return TvSeriesViewHolder(layout)
    }

}