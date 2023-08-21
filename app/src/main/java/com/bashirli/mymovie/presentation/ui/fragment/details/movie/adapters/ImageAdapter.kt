package com.bashirli.mymovie.presentation.ui.fragment.details.movie.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bashirli.mymovie.common.util.LIMIT_ITEM
import com.bashirli.mymovie.common.util.visible
import com.bashirli.mymovie.databinding.ItemImagesBinding
import com.bashirli.mymovie.domain.model.details.images.BackdropModel

class ImageAdapter : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    private val imageList=ArrayList<BackdropModel>()

    var onClickMoreButton:()->Unit={}

    inner class ImageViewHolder(private val binding:ItemImagesBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item:BackdropModel){
            binding.backdropModel=item
            binding.executePendingBindings()
        }

        fun setCard(onClickMoreButton:()->Unit={}){
            binding.apply {
                cardMore.visible()
                buttonMore.setOnClickListener {
                    onClickMoreButton()
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val layout=ItemImagesBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ImageViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val item = differ.currentList.get(position)
        holder.bind(item)
        if(position==itemCount-1 && position >= LIMIT_ITEM ) holder.setCard(onClickMoreButton)

    }

    private val imagesDU = object : DiffUtil.ItemCallback<BackdropModel>(){
        override fun areItemsTheSame(oldItem: BackdropModel, newItem: BackdropModel): Boolean {
            return oldItem.hashCode()==newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: BackdropModel, newItem: BackdropModel): Boolean {
            return oldItem==newItem
        }

    }

    private val differ=AsyncListDiffer(this,imagesDU)

    fun updateList(list:List<BackdropModel>){
        imageList.clear()
        imageList.addAll(list)
        if(list.size>5){
            differ.submitList(list.subList(0,5))
        }else{
            differ.submitList(list)
        }
    }

}