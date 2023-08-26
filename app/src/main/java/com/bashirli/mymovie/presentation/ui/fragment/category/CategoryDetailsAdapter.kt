package com.bashirli.mymovie.presentation.ui.fragment.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bashirli.mymovie.databinding.ItemCategoryDetailsBinding
import com.bashirli.mymovie.domain.model.categorySelected.CategoryDetailsModel
import com.bashirli.mymovie.domain.model.categorySelected.ItemModel

class CategoryDetailsAdapter : RecyclerView.Adapter<CategoryDetailsAdapter.CategoryViewHolder>() {

    var onClickMovieItem : (item:ItemModel) -> Unit = {}
    inner class CategoryViewHolder(private val binding : ItemCategoryDetailsBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item : ItemModel){
            binding.itemModel=item
            binding.executePendingBindings()
        }
        fun find(item: ItemModel,onClickMovieItem : (item:ItemModel) -> Unit = {}){
            binding.buttonGo.setOnClickListener {
                onClickMovieItem(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val layout = ItemCategoryDetailsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CategoryViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = differ.currentList[position]

        holder.bind(item)
        holder.find(item,onClickMovieItem)
    }

    private val categoryDU = object : DiffUtil.ItemCallback<ItemModel>() {
        override fun areItemsTheSame(
            oldItem: ItemModel,
            newItem: ItemModel,
        ): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ItemModel,
            newItem: ItemModel,
        ): Boolean {
            return oldItem==newItem
        }

    }


    private val differ = AsyncListDiffer(this,categoryDU)


    fun updateList ( list : List<ItemModel>){
        differ.submitList(list)
    }


}