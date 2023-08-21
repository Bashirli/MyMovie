package com.bashirli.mymovie.presentation.ui.fragment.details.movie.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bashirli.mymovie.databinding.ItemCrewBinding
import com.bashirli.mymovie.domain.model.details.cast.CrewModel


class CrewAdapter : RecyclerView.Adapter<CrewAdapter.CrewViewHolder>() {

    inner class CrewViewHolder(private val binding : ItemCrewBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: CrewModel){
            binding.crewModel=item
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrewViewHolder {
        val layout= ItemCrewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CrewViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: CrewViewHolder, position: Int) {
        val item=differ.currentList.get(position)
        holder.bind(item)
    }

    private val castDU=object: DiffUtil.ItemCallback<CrewModel>(){
        override fun areItemsTheSame(oldItem: CrewModel, newItem: CrewModel): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: CrewModel, newItem: CrewModel): Boolean {
            return oldItem==newItem
        }

    }

    private val differ= AsyncListDiffer(this,castDU)

    fun updateList(list:List<CrewModel>){
        differ.submitList(list)
    }

}