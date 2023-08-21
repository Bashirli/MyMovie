package com.bashirli.mymovie.presentation.ui.fragment.details.movie.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bashirli.mymovie.databinding.ItemGridImagesBinding
import com.bashirli.mymovie.domain.model.details.images.BackdropModel

class ImageGridAdapter : RecyclerView.Adapter<ImageGridAdapter.ImageViewHolder>() {


    var onLongClickImageListener : (BackdropModel) -> Unit = {}

    inner class ImageViewHolder(private val binding: ItemGridImagesBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: BackdropModel){
            binding.backdropModel=item
            binding.executePendingBindings()
        }

        fun find(item: BackdropModel,onLongClickImageListener : (BackdropModel) -> Unit = {}){
            binding.mainCard.setOnLongClickListener {
                onLongClickImageListener(item)
                return@setOnLongClickListener true
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val layout= ItemGridImagesBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ImageViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val item = differ.currentList.get(position)
        holder.bind(item)
        holder.find(item, onLongClickImageListener)

    }

    private val imagesDU = object : DiffUtil.ItemCallback<BackdropModel>(){
        override fun areItemsTheSame(oldItem: BackdropModel, newItem: BackdropModel): Boolean {
            return oldItem.hashCode()==newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: BackdropModel, newItem: BackdropModel): Boolean {
            return oldItem==newItem
        }

    }

    private val differ= AsyncListDiffer(this,imagesDU)

    fun updateList(list:List<BackdropModel>){
            differ.submitList(list)
    }

}