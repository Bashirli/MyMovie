package com.bashirli.mymovie.presentation.ui.fragment.home.movies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bashirli.mymovie.common.diffUtil.MoviesDiffUtilItemCallback
import com.bashirli.mymovie.databinding.ItemMovieBinding
import com.bashirli.mymovie.domain.model.movies.ResultModel

class MoviesAdapter : PagingDataAdapter<ResultModel, MoviesAdapter.MoviesViewHolder>(MoviesDiffUtilItemCallback) {

    var onClickMovieItem : (ResultModel) -> Unit = {}
    var onLongClickMovieItem : (ResultModel) -> Unit = {}

    inner class MoviesViewHolder(private val binding:ItemMovieBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item:ResultModel){
            binding.movieData=item
            binding.executePendingBindings()
        }
        fun find(
            item:ResultModel,
            onClickMovieItem : (ResultModel) -> Unit = {},
            onLongClickMovieItem : (ResultModel) -> Unit = {}
                 ){
            binding.cardView.setOnClickListener {
                onClickMovieItem(item)
            }
            binding.cardView.setOnLongClickListener {
                onLongClickMovieItem(item)
                return@setOnLongClickListener true
            }
        }
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {

        getItem(position)?.let { item ->
            holder.bind(item)
            holder.find(item,onClickMovieItem,onLongClickMovieItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val layout=ItemMovieBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MoviesViewHolder(layout)
    }


}