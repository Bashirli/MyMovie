package com.bashirli.mymovie.presentation.ui.fragment.home.movies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bashirli.mymovie.R
import com.bashirli.mymovie.databinding.ItemCategoryBinding
import com.bashirli.mymovie.domain.model.movies.GenreModel

class MovieCategoriesAdapter : RecyclerView.Adapter<MovieCategoriesAdapter.MoviesViewHolder>() {
    val imageList= listOf(R.drawable.action,R.drawable.horror,R.drawable.cartoons)

    var onClickCategoryItem:(GenreModel)->Unit={}

    inner class MoviesViewHolder(private val binding:ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item:GenreModel){
            binding.genreModel=item
            binding.executePendingBindings()
        }
        fun imageSet(position : Int){
            val image= imageList.get(position%imageList.size)
            binding.imageCat.setImageResource(image)
        }
        fun find(item:GenreModel,onClickCategoryItem:(GenreModel)->Unit={}){
            binding.cardView.setOnClickListener {
                onClickCategoryItem(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val layout=ItemCategoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MoviesViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val item = differ.currentList.get(position)
        holder.bind(item)
        holder.imageSet(position)
        holder.find(item,onClickCategoryItem)
    }

    private val movieCategoryDiffUtil=object:DiffUtil.ItemCallback<GenreModel>(){
        override fun areItemsTheSame(oldItem: GenreModel, newItem: GenreModel): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: GenreModel, newItem: GenreModel): Boolean {
            return oldItem==newItem
        }
    }

    private val differ=AsyncListDiffer(this,movieCategoryDiffUtil)

    fun updateList(list:List<GenreModel>){
        differ.submitList(list)
    }

}