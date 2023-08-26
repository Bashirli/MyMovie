package com.bashirli.mymovie.presentation.ui.fragment.details.movie.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bashirli.mymovie.databinding.ItemGenreBinding
import com.bashirli.mymovie.domain.model.details.movie.GenreDetailsModel

class GenreAdapter : RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {

    var onClickGenreItem : (Int) -> Unit = {}

    inner class GenreViewHolder(private val binding:ItemGenreBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item: GenreDetailsModel){
            binding.genreModel=item
            binding.executePendingBindings()
        }

        fun find(item : GenreDetailsModel,onClickGenreItem : (Int) -> Unit = {}){
            binding.chipButton.setOnClickListener {
                item.id?.let {
                    onClickGenreItem(it)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val layout=ItemGenreBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return GenreViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val item=differ.currentList.get(position)
        holder.bind(item)
        holder.find(item,onClickGenreItem)
    }

    private val genreDiffUtil=object:DiffUtil.ItemCallback<GenreDetailsModel>(){
        override fun areItemsTheSame(
            oldItem: GenreDetailsModel,
            newItem: GenreDetailsModel,
        ): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(
            oldItem: GenreDetailsModel,
            newItem: GenreDetailsModel,
        ): Boolean {
            return oldItem==newItem
        }

    }

    private val differ=AsyncListDiffer(this,genreDiffUtil)

    fun updateList(list:List<GenreDetailsModel>){
        differ.submitList(list)
    }

}