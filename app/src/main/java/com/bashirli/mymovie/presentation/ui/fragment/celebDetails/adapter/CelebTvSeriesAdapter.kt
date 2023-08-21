package com.bashirli.mymovie.presentation.ui.fragment.celebDetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bashirli.mymovie.data.dto.celebs.tvseries.CelebTvCast
import com.bashirli.mymovie.databinding.ItemCelebMovieBinding
import com.bashirli.mymovie.databinding.ItemCelebTvSeriesBinding
import com.bashirli.mymovie.domain.model.celebs.movies.CelebCastModel
import com.bashirli.mymovie.domain.model.celebs.tvSeries.CelebTvCastModel

class CelebTvSeriesAdapter : RecyclerView.Adapter<CelebTvSeriesAdapter.CelebTvSeriesViewHolder>() {

    var onClickTvSeriesItem : (CelebTvCastModel) -> Unit = {}

    inner class CelebTvSeriesViewHolder(private val binding: ItemCelebTvSeriesBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: CelebTvCastModel){
            binding.movieData=item
            binding.executePendingBindings()
        }

        fun find(item: CelebTvCastModel, onClickTvSeriesItem : (CelebTvCastModel) -> Unit = {}){
            binding.mainRoot.setOnClickListener{
                onClickTvSeriesItem(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CelebTvSeriesViewHolder {
        val layout= ItemCelebTvSeriesBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CelebTvSeriesViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: CelebTvSeriesViewHolder, position: Int) {
        val item = differ.currentList[position]

        with(holder){
            find(item,onClickTvSeriesItem)
            bind(item)
        }

    }

    private val celebCastDU=object: DiffUtil.ItemCallback<CelebTvCastModel>(){
        override fun areItemsTheSame(oldItem: CelebTvCastModel, newItem: CelebTvCastModel): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: CelebTvCastModel, newItem: CelebTvCastModel): Boolean {
            return oldItem==newItem
        }

    }

    private val differ = AsyncListDiffer(this,celebCastDU)

    fun updateList(list:List<CelebTvCastModel>){
        differ.submitList(list)
    }

}