package com.bashirli.mymovie.presentation.ui.fragment.details.movie.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bashirli.mymovie.databinding.ItemCastBinding
import com.bashirli.mymovie.domain.model.details.cast.CastModel

class CastAdapter : RecyclerView.Adapter<CastAdapter.CastViewHolder>() {

    var onClickCardListener : (CastModel) -> Unit = {}

    inner class CastViewHolder(private val binding : ItemCastBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item:CastModel){
            binding.castModel=item
            binding.executePendingBindings()
        }
        fun find(item : CastModel,onClickCardListener : (CastModel) -> Unit = {}){
            binding.mainCard.setOnClickListener {
                onClickCardListener(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val layout=ItemCastBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CastViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        val item=differ.currentList.get(position)
        holder.bind(item)
        holder.find(item,onClickCardListener)
    }

    private val castDU=object:DiffUtil.ItemCallback<CastModel>(){
        override fun areItemsTheSame(oldItem: CastModel, newItem: CastModel): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: CastModel, newItem: CastModel): Boolean {
            return oldItem==newItem
        }

    }

    private val differ=AsyncListDiffer(this,castDU)

    fun updateList(list:List<CastModel>){
        differ.submitList(list)
    }

}