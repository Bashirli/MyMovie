package com.bashirli.mymovie.presentation.ui.fragment.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bashirli.mymovie.common.diffUtil.MoviesDiffUtilItemCallback
import com.bashirli.mymovie.databinding.ItemSearchBinding
import com.bashirli.mymovie.domain.model.movies.ResultModel

class SearchPagingAdapter : PagingDataAdapter<ResultModel,SearchPagingAdapter.SearchViewHolder>(
    MoviesDiffUtilItemCallback
) {

    var onClickGoButtonListener : (ResultModel) -> Unit = {}
    var onLongClickCardListener : (ResultModel) -> Unit = {}

    inner class SearchViewHolder(private val binding:ItemSearchBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item:ResultModel){
            binding.resultModel=item
            binding.executePendingBindings()
        }

        fun find(
            item : ResultModel,
            onClickGoButtonListener : (ResultModel) -> Unit = {},
            onLongClickCardListener : (ResultModel) -> Unit = {}
            ){
            with(binding){
                buttonGo.setOnClickListener {
                    onClickGoButtonListener(item)
                }
                mainCard.setOnLongClickListener {
                    onLongClickCardListener(item)
                    return@setOnLongClickListener true
                }
            }
        }

    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        getItem(position)?.let {
            val item = it
            holder.bind(item)
            holder.find(item, onClickGoButtonListener, onLongClickCardListener)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val layout=ItemSearchBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SearchViewHolder(layout)

    }

}