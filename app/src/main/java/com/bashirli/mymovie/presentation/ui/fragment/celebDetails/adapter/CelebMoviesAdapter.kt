package com.bashirli.mymovie.presentation.ui.fragment.celebDetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bashirli.mymovie.databinding.ItemCelebMovieBinding
import com.bashirli.mymovie.domain.model.celebs.movies.CelebCastModel

class CelebMoviesAdapter : RecyclerView.Adapter<CelebMoviesAdapter.CelebMoviesViewHolder>() {

    var onClickMovieItem : (CelebCastModel) -> Unit = {}

    inner class CelebMoviesViewHolder(private val binding:ItemCelebMovieBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item:CelebCastModel){
            binding.movieData=item
            binding.executePendingBindings()
        }
        fun find(item:CelebCastModel,onClickMovieItem : (CelebCastModel) -> Unit = {}){
            binding.mainRoot.setOnClickListener{
                onClickMovieItem(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CelebMoviesViewHolder {
        val layout=ItemCelebMovieBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CelebMoviesViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: CelebMoviesViewHolder, position: Int) {
        val item = differ.currentList[position]

        with(holder){
            find(item,onClickMovieItem)
            bind(item)
        }

    }

    private val celebCastDU=object:DiffUtil.ItemCallback<CelebCastModel>(){
        override fun areItemsTheSame(oldItem: CelebCastModel, newItem: CelebCastModel): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: CelebCastModel, newItem: CelebCastModel): Boolean {
            return oldItem==newItem
        }

    }

    private val differ = AsyncListDiffer(this,celebCastDU)

    fun updateList(list:List<CelebCastModel>){
        differ.submitList(list)
    }

}